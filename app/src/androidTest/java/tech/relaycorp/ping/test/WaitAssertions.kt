package tech.relaycorp.ping.test

import kotlinx.coroutines.runBlocking

object WaitAssertions {

    fun waitFor(check: () -> Unit) {
        val initialTime = System.currentTimeMillis()
        var lastError: Throwable?
        do {
            try {
                check.invoke()
                return
            } catch (throwable: Throwable) {
                lastError = throwable
            }
            Thread.sleep(INTERVAL)
        } while (System.currentTimeMillis() - initialTime < TIMEOUT)
        throw AssertionError("Timeout waiting", lastError)
    }

    fun suspendWaitFor(check: suspend () -> Unit) =
        waitFor { runBlocking { check.invoke() } }

    private const val TIMEOUT = 10_000L
    private const val INTERVAL = TIMEOUT / 20
}
