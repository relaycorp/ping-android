package tech.relaycorp.ping.common.di

import dagger.Component
import tech.relaycorp.ping.App
import tech.relaycorp.ping.AppModule
import tech.relaycorp.ping.awala.AwalaModule
import tech.relaycorp.ping.data.DataModule
import tech.relaycorp.ping.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        AwalaModule::class
    ]
)
interface AppComponent {
    fun activityComponent(): ActivityComponent
    fun inject(app: App)
    fun inject(activity: MainActivity)
}
