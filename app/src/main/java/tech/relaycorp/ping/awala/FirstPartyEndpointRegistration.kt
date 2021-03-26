package tech.relaycorp.ping.awala

import tech.relaycorp.awaladroid.endpoint.FirstPartyEndpoint

interface FirstPartyEndpointRegistration {
    suspend fun register(): FirstPartyEndpoint
}
