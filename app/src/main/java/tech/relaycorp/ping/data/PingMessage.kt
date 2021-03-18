package tech.relaycorp.ping.data

import java.util.*

data class PingMessage(
    val id: String = UUID.randomUUID().toString(),
    val sent: Long = System.currentTimeMillis(),
    val received: Long? = null
)
