package tech.relaycorp.ping.domain.model

import java.time.ZonedDateTime

data class Ping(
    val messageId: String,
    val peer: Peer,
    val sentAt: ZonedDateTime,
    val pongReceivedAt: ZonedDateTime? = null
)
