package tech.relaycorp.ping.common.di

import dagger.Subcomponent
import tech.relaycorp.gateway.common.di.PerActivity
import tech.relaycorp.ping.ui.main.MainActivity
import tech.relaycorp.ping.ui.peers.AddPublicPeerActivity
import tech.relaycorp.ping.ui.peers.PeerActivity
import tech.relaycorp.ping.ui.peers.PeersActivity
import tech.relaycorp.ping.ui.ping.PingActivity

@PerActivity
@Subcomponent
interface ActivityComponent {

    // Activities

    fun inject(activity: AddPublicPeerActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: PeerActivity)
    fun inject(activity: PeersActivity)
    fun inject(activity: PingActivity)
}
