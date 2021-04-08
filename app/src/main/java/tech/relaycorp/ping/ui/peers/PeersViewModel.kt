package tech.relaycorp.ping.ui.peers

import kotlinx.coroutines.flow.*
import tech.relaycorp.ping.ui.BaseViewModel
import tech.relaycorp.ping.domain.ObservePeers
import tech.relaycorp.ping.domain.model.Peer
import javax.inject.Inject

class PeersViewModel
@Inject constructor(
    observePeers: ObservePeers
) : BaseViewModel() {

    private val _peers = MutableStateFlow(emptyList<Peer>())
    fun peers(): Flow<List<Peer>> = _peers.asStateFlow()

    init {
        observePeers
            .observe()
            .onEach { _peers.value = it }
            .launchIn(backgroundScope)
    }
}
