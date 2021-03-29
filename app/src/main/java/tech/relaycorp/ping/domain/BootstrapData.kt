package tech.relaycorp.ping.domain

import android.content.res.Resources
import kotlinx.coroutines.flow.first
import tech.relaycorp.ping.R
import tech.relaycorp.ping.awala.FirstPartyEndpointRegistration
import tech.relaycorp.ping.data.preference.AppPreferences
import javax.inject.Inject

class BootstrapData
@Inject constructor(
    private val resources: Resources,
    private val appPreferences: AppPreferences,
    private val addPublicPeer: AddPublicPeer,
    private val firstPartyEndpointRegistration: FirstPartyEndpointRegistration
) {

    suspend fun bootstrapIfNeeded() {
        if (appPreferences.firstPartyEndpointAddress().first() != null) return

        importDefaultPublicPeer()
        val endpoint = firstPartyEndpointRegistration.register()
        appPreferences.setFirstPartyEndpointAddress(endpoint.privateAddress)
    }

    private suspend fun importDefaultPublicPeer() {
        addPublicPeer.add(
            "ping.awala.services",
            resources.openRawResource(R.raw.ping_awala_identity).use { it.readBytes() }
        )
    }
}
