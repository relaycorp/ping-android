package tech.relaycorp.ping.data.preference

import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Provider

class AppPreferences
@Inject constructor(
    private val preferences: Provider<FlowSharedPreferences>
) {

    private val firstPartyEndpointAddress by lazy {
        preferences.get().getString("first_party_endpoint")
    }

    fun firstPartyEndpointAddress(): Flow<String?> =
        { firstPartyEndpointAddress }.asFlow()
            .flatMapLatest { it.asFlow() }
            .map { it.ifEmpty { null } }

    suspend fun setFirstPartyEndpointAddress(value: String) =
        firstPartyEndpointAddress.setAndCommit(value)
}
