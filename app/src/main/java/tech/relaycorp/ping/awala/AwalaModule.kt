package tech.relaycorp.ping.awala

import dagger.Module
import dagger.Provides
import tech.relaycorp.awaladroid.endpoint.FirstPartyEndpoint

@Module
class AwalaModule {

    @Provides
    fun firstPartyEndpointRegistration(): FirstPartyEndpointRegistration =
        object : FirstPartyEndpointRegistration {
            override suspend fun register(): FirstPartyEndpoint =
                FirstPartyEndpoint.register()
        }
}
