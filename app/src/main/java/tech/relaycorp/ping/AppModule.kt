package tech.relaycorp.ping

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.tfcporciuncula.flow.FlowSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun sharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("ping", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun flowSharedPreferences(sharedPreferences: SharedPreferences) =
        FlowSharedPreferences(sharedPreferences)
}
