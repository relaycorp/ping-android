package tech.relaycorp.ping.awala

import tech.relaycorp.awaladroid.endpoint.FirstPartyEndpoint
import tech.relaycorp.awaladroid.endpoint.ThirdPartyEndpoint
import tech.relaycorp.awaladroid.messaging.OutgoingMessage
import tech.relaycorp.awaladroid.messaging.ParcelId
import java.time.ZonedDateTime

interface OutgoingMessageBuilder {
    suspend fun build(
        type: String,
        content: ByteArray,
        senderEndpoint: FirstPartyEndpoint,
        recipientEndpoint: ThirdPartyEndpoint,
        parcelExpiryDate: ZonedDateTime,
        parcelId: ParcelId
    ): OutgoingMessage
}
