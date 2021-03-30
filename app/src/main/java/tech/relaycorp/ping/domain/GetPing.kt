package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import tech.relaycorp.ping.data.database.dao.PingDao
import javax.inject.Inject

class GetPing
@Inject constructor(
    private val pingDao: PingDao
) {
    fun get(pingId: String) =
        pingDao
            .getPublic(pingId)
            .filterNotNull()
            .map { it.toModel() }
}
