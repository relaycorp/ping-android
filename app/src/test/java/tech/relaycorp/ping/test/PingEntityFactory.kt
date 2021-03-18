package tech.relaycorp.ping.test

import tech.relaycorp.ping.data.database.entity.PingEntity
import tech.relaycorp.ping.data.database.entity.PublicPeerEntity
import tech.relaycorp.ping.domain.model.PeerType
import java.time.ZonedDateTime
import java.util.*

object PingEntityFactory {
    fun build(peer: PublicPeerEntity): PingEntity {
        return PingEntity(
            messageId = UUID.randomUUID().toString(),
            peerPrivateAddress = peer.privateAddress,
            peerType = PeerType.Public,
            sentAt = ZonedDateTime.now()
        )
    }
}
