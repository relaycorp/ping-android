package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.map
import tech.relaycorp.ping.data.database.dao.PingDao
import tech.relaycorp.ping.domain.model.Peer
import tech.relaycorp.ping.domain.model.PeerType
import tech.relaycorp.ping.domain.model.Ping
import javax.inject.Inject

class ObservePings
@Inject constructor(
    private val pingDao: PingDao
) {

    fun observe() = pingDao.listPublic().map { list ->
        list.map {
            Ping(
                pingId = it.ping.pingId,
                peer = Peer(
                    privateAddress = it.publicPeer.privateAddress,
                    alias = it.publicPeer.publicAddress,
                    peerType = PeerType.Public
                ),
                sentAt = it.ping.sentAt,
                pongReceivedAt = it.ping.pongReceivedAt
            )
        }
    }
}
