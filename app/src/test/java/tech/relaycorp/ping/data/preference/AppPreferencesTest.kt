package tech.relaycorp.ping.data.preference

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import tech.relaycorp.ping.App
import java.util.*

@RunWith(RobolectricTestRunner::class)
internal class AppPreferencesTest {

    private val sharedPreferences =
        ApplicationProvider.getApplicationContext<App>()
            .getSharedPreferences("test", Context.MODE_PRIVATE)
    private val coroutineContext = TestCoroutineScope().coroutineContext
    private val flowSharedPreferences = FlowSharedPreferences(sharedPreferences, coroutineContext)
    private val appPreferences = AppPreferences { flowSharedPreferences }

    @Test
    fun getAndSet() = runBlockingTest(coroutineContext) {
        sharedPreferences.edit().clear().commit()
        assertNull(appPreferences.firstPartyEndpointAddress().first())

        val address = UUID.randomUUID().toString()
        appPreferences.setFirstPartyEndpointAddress(address)
        assertEquals(address, appPreferences.firstPartyEndpointAddress().first())
    }
}
