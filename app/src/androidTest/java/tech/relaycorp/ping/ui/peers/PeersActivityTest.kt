package tech.relaycorp.ping.ui.peers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import tech.relaycorp.ping.test.AppTestProvider.component
import tech.relaycorp.ping.test.BaseActivityTestRule
import tech.relaycorp.ping.test.PublicPeerEntityFactory
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class PeersActivityTest {

    @Rule
    @JvmField
    val testRule = BaseActivityTestRule(PeersActivity::class, false)

    @Inject
    lateinit var publicPeerDao: PublicPeerDao

    @Before
    fun setUp() {
        component.inject(this)
    }

    @Test
    fun showsPublicPeers() {
        val peer = PublicPeerEntityFactory.build()
        runBlocking {
            publicPeerDao.save(peer)
        }

        testRule.start()

        assertDisplayed(peer.publicAddress)
    }
}
