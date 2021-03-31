package tech.relaycorp.ping.test

import androidx.test.espresso.intent.Intents
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class IntentsRule : TestRule {
    override fun apply(base: Statement, description: Description?) =
        object : Statement() {
            override fun evaluate() {
                Intents.init()
                base.evaluate()
                Intents.release()
            }
        }
}
