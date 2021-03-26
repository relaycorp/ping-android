package tech.relaycorp.ping.ui.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.relaycorp.gateway.ui.BaseViewModel
import tech.relaycorp.ping.domain.ObservePings
import tech.relaycorp.ping.domain.model.Ping
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    observePings: ObservePings
) : BaseViewModel() {

    private val _pings = MutableStateFlow(emptyList<Ping>())
    fun pings() = _pings.asStateFlow()

    init {
        observePings
            .observe()
            .onEach { _pings.value = it }
            .launchIn(backgroundScope)
    }
}
