package tech.relaycorp.ping.domain

import kotlinx.coroutines.flow.map
import tech.relaycorp.ping.data.database.dao.PingDao
import javax.inject.Inject

class ObservePings
@Inject constructor(
    private val pingDao: PingDao
) {
    fun observe() =
        pingDao.listPublic()
            .map { list ->
                list.map { it.toModel() }
            }
}
