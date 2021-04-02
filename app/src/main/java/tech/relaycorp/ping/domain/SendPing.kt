package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.first
import tech.relaycorp.awaladroid.AwaladroidException
import tech.relaycorp.awaladroid.messaging.ParcelId
import tech.relaycorp.ping.awala.*
import tech.relaycorp.ping.data.database.dao.PingDao
import tech.relaycorp.ping.data.database.entity.PingEntity
import tech.relaycorp.ping.data.preference.AppPreferences
import tech.relaycorp.ping.domain.model.Peer
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.time.Duration

class SendPing
@Inject constructor(
    private val appPreferences: AppPreferences,
    private val firstPartyEndpointLoad: FirstPartyEndpointLoad,
    private val publicThirdPartyEndpointLoad: PublicThirdPartyEndpointLoad,
    private val sendGatewayMessage: SendGatewayMessage,
    private val pingSerialization: PingSerialization,
    private val outgoingMessageBuilder: OutgoingMessageBuilder,
    private val pingDao: PingDao
) {

    @Throws(SendPingException::class)
    suspend fun send(peer: Peer, duration: Duration): String {
        val senderAddress = appPreferences.firstPartyEndpointAddress().first()
            ?: throw SendPingException("Sender not set")
        val sender = firstPartyEndpointLoad.load(senderAddress)
            ?: throw SendPingException("Sender not registered")

        val recipient = publicThirdPartyEndpointLoad.load(peer.privateAddress)
            ?: throw SendPingException("Recipient not imported")

        val parcelId = ParcelId.generate()
        val expiresAt = ZonedDateTime.now().plusSeconds(duration.inSeconds.toLong())
        val authorization = sender.issueAuthorization(
            recipient,
            ZonedDateTime.now().plusSeconds(duration.inSeconds.toLong())
        )
        val pingMessageSerialized = pingSerialization.serialize(
            parcelId.value,
            authorization.pdaSerialized,
            authorization.pdaChainSerialized
        )
        val outgoingMessage = outgoingMessageBuilder.build(
            AwalaPing.V1.PingType,
            pingMessageSerialized,
            sender,
            recipient,
            expiresAt,
            parcelId
        )
        try {
            sendGatewayMessage.send(outgoingMessage)
        } catch (e: AwaladroidException) {
            throw SendPingException("Could not send message to gateway", e)
        }

        val ping = PingEntity(
            pingId = parcelId.value,
            peerPrivateAddress = peer.privateAddress,
            peerType = peer.peerType,
            sentAt = ZonedDateTime.now(),
            expiresAt = expiresAt
        )
        pingDao.save(ping)

        appPreferences.setLastRecipient(peer.privateAddress, peer.peerType)

        return ping.pingId
    }
}

class SendPingException(message: String, cause: Throwable? = null) : Exception(message, cause)
