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
import tech.relaycorp.ping.data.database.entity.PublicPeerEntity
import tech.relaycorp.ping.test.PublicPeerEntityFactory

@RunWith(RobolectricTestRunner::class)
class PublicPeerDaoTest {

    private val dao = Room
        .inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
        .build()
        .publicPeerDao()

    @Test
    internal fun saveAndList() = runBlocking {
        assertEquals(emptyList<PublicPeerEntity>(), dao.list().first())

        val peer = PublicPeerEntityFactory.build()
        dao.save(peer)

        assertEquals(listOf(peer), dao.list().first())
    }

    @Test
    internal fun delete() = runBlocking {
        val peer = PublicPeerEntityFactory.build()
        dao.save(peer)
        dao.delete(peer)
        assertEquals(emptyList<PublicPeerEntity>(), dao.list().first())
    }
}
