package tech.relaycorp.ping

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    private val app: App
) {

    @Provides
    fun app() = app

    @Provides
    fun context(): Context = app

    @Provides
    fun resources(): Resources = app.resources
}
