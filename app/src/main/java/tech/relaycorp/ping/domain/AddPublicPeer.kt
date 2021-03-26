package tech.relaycorp.ping.domain

import tech.relaycorp.awaladroid.endpoint.PublicThirdPartyEndpoint
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import tech.relaycorp.ping.data.database.entity.PublicPeerEntity
import javax.inject.Inject

class AddPublicPeer
@Inject constructor(
    private val publicPeerDao: PublicPeerDao
) {

    suspend fun add(address: String, identity: ByteArray): PublicThirdPartyEndpoint {
        val endpoint = PublicThirdPartyEndpoint.import(address, identity)
        publicPeerDao.save(
            PublicPeerEntity(
                privateAddress = endpoint.privateAddress,
                publicAddress = endpoint.publicAddress
            )
        )
        return endpoint
    }
}
