package tech.relaycorp.ping.awala

import tech.relaycorp.awaladroid.messaging.OutgoingMessage

interface SendGatewayMessage {
    suspend fun send(outgoingMessage: OutgoingMessage)
}
