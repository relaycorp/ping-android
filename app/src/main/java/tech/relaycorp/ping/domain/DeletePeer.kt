package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.first
import tech.relaycorp.awaladroid.endpoint.PublicThirdPartyEndpoint
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import javax.inject.Inject

class DeletePeer
@Inject constructor(
    private val publicPeerDao: PublicPeerDao
) {

    suspend fun delete(privateAddress: String) {
        val entity = publicPeerDao.get(privateAddress).first() ?: return
        PublicThirdPartyEndpoint.load(entity.privateAddress)?.delete()
        publicPeerDao.delete(entity)
    }
}
