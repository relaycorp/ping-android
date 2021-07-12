package tech.relaycorp.ping.data.preference

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import kotlinx.coroutines.flow.*
import tech.relaycorp.ping.domain.model.PeerType
import javax.inject.Inject
import javax.inject.Provider

class AppPreferences
@Inject constructor(
    private val preferences: Provider<FlowSharedPreferences>
) {

    // First Party Endpoint
    // this will send all pings and receive all pongs

    private val firstPartyEndpointAddress by lazy {
        preferences.get().getString("first_party_endpoint")
    }

    fun firstPartyEndpointAddress(): Flow<String?> =
        { firstPartyEndpointAddress }.asFlow()
            .flatMapLatest { it.asFlow() }
            .map { it.ifEmpty { null } }

    suspend fun setFirstPartyEndpointAddress(value: String) =
        firstPartyEndpointAddress.setAndCommit(value)

    // Last recipient pinged

    private val lastRecipientAddress by lazy {
        preferences.get().getString("last_recipient_address", "")
    }
    private val lastRecipientType by lazy {
        preferences.get().getEnum("last_recipient_type", PeerType.Public)
    }

    fun lastRecipient(): Flow<Pair<String, PeerType>?> =
        { lastRecipientAddress }.asFlow().flatMapLatest { it.asFlow() }
            .combine(
                { lastRecipientType }.asFlow().flatMapLatest { it.asFlow() },
            ) { address, type ->
                if (address.isNotEmpty()) {
                    Pair(address, type)
                } else {
                    null
                }
            }

    suspend fun setLastRecipient(address: String, type: PeerType) {
        lastRecipientAddress.setAndCommit(address)
        lastRecipientType.setAndCommit(type)
    }
}
