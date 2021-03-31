package tech.relaycorp.ping.ui.peers

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaEnabledAssertions.assertDisabled
import com.schibsted.spain.barista.assertion.BaristaEnabledAssertions.assertEnabled
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.relaycorp.ping.R
import tech.relaycorp.ping.test.ActivityAssertions.waitForCurrentActivityToBe
import tech.relaycorp.ping.test.AppTestProvider.component
import tech.relaycorp.ping.test.AppTestProvider.context
import tech.relaycorp.ping.test.BaseActivityTestRule

@RunWith(AndroidJUnit4::class)
class AddPublicPeerActivityTest {

    @Rule
    @JvmField
    val testRule = BaseActivityTestRule(PeersActivity::class, false)

    @Before
    fun setUp() {
        component.inject(this)
    }

    @Test
    fun addPublicPeerSuccessfully() {
        testRule.start()
        clickOn(R.id.addPeer)
        clickOn(R.string.peer_public)

        assertDisabled(R.id.save)

        val address = "ping.awala.services"
        writeTo(R.id.addressEdit, address)

        intending(hasAction(Intent.ACTION_OPEN_DOCUMENT))
            .respondWith(
                Instrumentation.ActivityResult(
                    Activity.RESULT_OK,
                    Intent().setData(
                        Uri.parse("android.resource://${context.packageName}/${R.raw.ping_awala_identity}")
                    )
                )
            )
        clickOn(R.string.peer_certificate_button)

        assertEnabled(R.id.save)
        clickOn(R.id.save)

        waitForCurrentActivityToBe(PeersActivity::class)
        assertDisplayed(address)
    }

    @Test
    fun addPublicPeerFailure() {
        testRule.start()
        clickOn(R.id.addPeer)
        clickOn(R.string.peer_public)

        val address = "ping.awala.services"
        writeTo(R.id.addressEdit, address)

        intending(hasAction(Intent.ACTION_OPEN_DOCUMENT))
            .respondWith(
                Instrumentation.ActivityResult(
                    Activity.RESULT_OK,
                    Intent().setData(
                        Uri.parse("android.resource://invalid")
                    )
                )
            )
        clickOn(R.string.peer_certificate_button)

        clickOn(R.id.save)
        assertDisplayed(R.string.peer_add_error)
    }
}
