package tech.relaycorp.ping.di

import dagger.Component
import tech.relaycorp.ping.App
import tech.relaycorp.ping.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)
    fun inject(activity: MainActivity)
}
