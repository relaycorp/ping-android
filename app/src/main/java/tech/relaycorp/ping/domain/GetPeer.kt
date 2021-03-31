package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import javax.inject.Inject

class GetPeer
@Inject constructor(
    private val publicPeerDao: PublicPeerDao
) {

    fun get(address: String) =
        publicPeerDao
            .get(address)
            .map { it?.toModel() }
            .filterNotNull()
}
