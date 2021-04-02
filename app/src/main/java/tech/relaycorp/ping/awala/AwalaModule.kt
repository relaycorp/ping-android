package tech.relaycorp.ping.awala

import dagger.Module
import dagger.Provides
import tech.relaycorp.awaladroid.GatewayClient
import tech.relaycorp.awaladroid.endpoint.FirstPartyEndpoint
import tech.relaycorp.awaladroid.endpoint.PublicThirdPartyEndpoint
import tech.relaycorp.awaladroid.endpoint.ThirdPartyEndpoint
import tech.relaycorp.awaladroid.messaging.OutgoingMessage
import tech.relaycorp.awaladroid.messaging.ParcelId
import java.time.ZonedDateTime

@Module
class AwalaModule {

    @Provides
    fun firstPartyEndpointRegistration(): FirstPartyEndpointRegistration =
        object : FirstPartyEndpointRegistration {
            override suspend fun register(): FirstPartyEndpoint =
                FirstPartyEndpoint.register()
        }

    @Provides
    fun firstPartyEndpointLoad(): FirstPartyEndpointLoad =
        object : FirstPartyEndpointLoad {
            override suspend fun load(privateAddress: String): FirstPartyEndpoint? =
                FirstPartyEndpoint.load(privateAddress)
        }

    @Provides
    fun publicThirdPartyEndpointLoad(): PublicThirdPartyEndpointLoad =
        object : PublicThirdPartyEndpointLoad {
            override suspend fun load(privateAddress: String): PublicThirdPartyEndpoint? =
                PublicThirdPartyEndpoint.load(privateAddress)
        }

    @Provides
    fun OutgoingMessageBuilder(): OutgoingMessageBuilder =
        object : OutgoingMessageBuilder {
            override suspend fun build(
                type: String,
                content: ByteArray,
                senderEndpoint: FirstPartyEndpoint,
                recipientEndpoint: ThirdPartyEndpoint,
                parcelExpiryDate: ZonedDateTime,
                parcelId: ParcelId
            ): OutgoingMessage =
                OutgoingMessage.build(
                    type, content, senderEndpoint, recipientEndpoint, parcelExpiryDate, parcelId
                )
        }

    @Provides
    fun sendGatewayMessage(): SendGatewayMessage = object : SendGatewayMessage {
        override suspend fun send(outgoingMessage: OutgoingMessage) {
            GatewayClient.sendMessage(outgoingMessage)
        }
    }
}
