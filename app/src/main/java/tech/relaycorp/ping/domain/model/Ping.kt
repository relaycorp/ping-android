package tech.relaycorp.ping.domain.model

import java.time.ZonedDateTime

data class Ping(
    val pingId: String,
    val peer: Peer,
    val sentAt: ZonedDateTime,
    val pongReceivedAt: ZonedDateTime? = null
) {

    val pongReceived get() = pongReceivedAt != null

}
