package tech.relaycorp.ping.test

import dagger.Component
import tech.relaycorp.ping.AppModule
import tech.relaycorp.ping.awala.AwalaModule
import tech.relaycorp.ping.common.di.AppComponent
import tech.relaycorp.ping.ui.main.MainActivityTest
import tech.relaycorp.ping.ui.ping.PingActivityTest
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, TestDataModule::class, AwalaModule::class])
interface AppTestComponent : AppComponent {
    fun inject(app: TestApp)

    // Rules

    fun inject(rule: ClearTestDatabaseRule)

    // Tests

    fun inject(test: MainActivityTest)
    fun inject(test: PingActivityTest)
}
