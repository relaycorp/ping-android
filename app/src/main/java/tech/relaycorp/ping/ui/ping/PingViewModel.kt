package tech.relaycorp.ping.ui.ping

import kotlinx.coroutines.flow.*
import tech.relaycorp.gateway.ui.BaseViewModel
import tech.relaycorp.ping.common.element
import tech.relaycorp.ping.domain.GetPing
import tech.relaycorp.ping.domain.model.Ping
import java.util.*
import javax.inject.Inject

class PingViewModel
@Inject constructor(
    private val getPing: GetPing
) : BaseViewModel() {

    private val pingId = MutableStateFlow(Optional.empty<String>())
    fun pingIdReceived(value: String) {
        pingId.value = Optional.of(value)
    }

    private val _ping = MutableStateFlow(Optional.empty<Ping>())
    fun ping() = _ping.asStateFlow().element()

    init {
        pingId
            .element()
            .flatMapLatest(getPing::get)
            .onEach { _ping.value = Optional.of(it) }
            .launchIn(backgroundScope)
    }
}
