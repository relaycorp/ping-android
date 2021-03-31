package tech.relaycorp.ping.test

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Assert
import tech.relaycorp.ping.test.WaitAssertions.waitFor
import kotlin.reflect.KClass

object ActivityAssertions {
    val currentActivity: Activity?
        get() {
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()
            val activity = arrayOfNulls<Activity>(1)
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                val activities =
                    ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(Stage.RESUMED)
                if (activities.iterator().hasNext()) {
                    activity[0] = activities.iterator().next()
                }
            }
            return activity[0]
        }

    fun assertCurrentActivity(activityKlass: KClass<out Activity>) {
        Assert.assertEquals(activityKlass.java.name, currentActivity?.componentName?.className)
    }

    fun waitForCurrentActivityToBe(activityKlass: KClass<out Activity>) {
        waitFor { assertCurrentActivity(activityKlass) }
    }
}
