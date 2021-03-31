package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.map
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import javax.inject.Inject

class ObservePeers
@Inject constructor(
    private val publicPeerDao: PublicPeerDao
) {

    fun observe() =
        publicPeerDao
            .list()
            .map { list -> list.map { it.toModel() } }
}
