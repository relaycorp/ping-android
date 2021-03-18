package tech.relaycorp.ping.common.di

import dagger.Subcomponent
import tech.relaycorp.gateway.common.di.PerActivity
import tech.relaycorp.ping.ui.main.MainActivity

@PerActivity
@Subcomponent
interface ActivityComponent {

    // Activities

    fun inject(activity: MainActivity)
}
