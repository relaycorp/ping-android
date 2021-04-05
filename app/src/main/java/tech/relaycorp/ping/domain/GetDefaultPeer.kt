package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.first
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import tech.relaycorp.ping.data.preference.AppPreferences
import tech.relaycorp.ping.domain.model.Peer
import javax.inject.Inject

class GetDefaultPeer
@Inject constructor(
    private val appPreferences: AppPreferences,
    private val publicPeerDao: PublicPeerDao
) {

    suspend fun get(): Peer? {
        return appPreferences.lastRecipient().first()
            ?.let { lastRecipient ->
                val entity = publicPeerDao.get(lastRecipient.first).first()
                if (entity != null && !entity.deleted) {
                    entity.toModel()
                } else null
            }
            ?: publicPeerDao.list().first().firstOrNull()?.toModel()
    }
}
