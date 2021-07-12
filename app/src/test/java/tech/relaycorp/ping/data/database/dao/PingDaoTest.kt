package tech.relaycorp.ping.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import tech.relaycorp.ping.data.database.AppDatabase
import tech.relaycorp.ping.data.database.entity.PingWithPublicPeer
import tech.relaycorp.ping.test.PingEntityFactory
import tech.relaycorp.ping.test.PublicPeerEntityFactory

@RunWith(RobolectricTestRunner::class)
class PingDaoTest {

    private val db = Room
        .inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
        .build()

    private val pingDao = db.pingDao()
    private val peerDao = db.publicPeerDao()

    @Test
    fun saveAndList() = runBlocking {
        assertEquals(emptyList<PingWithPublicPeer>(), pingDao.listPublic().first())

        val peer = PublicPeerEntityFactory.build()
        val ping = PingEntityFactory.build(peer)
        peerDao.save(peer)
        pingDao.save(ping)

        assertEquals(listOf(PingWithPublicPeer(ping, peer)), pingDao.listPublic().first())
    }
}
