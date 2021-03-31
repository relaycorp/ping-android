package tech.relaycorp.ping

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import tech.relaycorp.awaladroid.Awala
import tech.relaycorp.ping.common.di.AppComponent
import tech.relaycorp.ping.common.di.DaggerAppComponent
import tech.relaycorp.ping.domain.BootstrapData
import javax.inject.Inject

open class App : Application() {

    val coroutineContext = Dispatchers.Default + SupervisorJob()

    open val component: AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    @Inject
    lateinit var bootstrapData: BootstrapData

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

        CoroutineScope(coroutineContext).launch {
            setupAwala()
        }
    }

    protected open suspend fun setupAwala() {
        Awala.setup(this)
        // bootstrapData.bootstrapIfNeeded()
    }
}
