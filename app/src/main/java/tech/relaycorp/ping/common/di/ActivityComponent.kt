package tech.relaycorp.ping.common.di

import dagger.Subcomponent
import tech.relaycorp.ping.ui.main.MainActivity
import tech.relaycorp.ping.ui.peers.AddPublicPeerActivity
import tech.relaycorp.ping.ui.peers.PeerActivity
import tech.relaycorp.ping.ui.peers.PeersActivity
import tech.relaycorp.ping.ui.ping.PingActivity
import tech.relaycorp.ping.ui.ping.SendPingActivity

@PerActivity
@Subcomponent
interface ActivityComponent {

    // Activities

    fun inject(activity: AddPublicPeerActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: PeerActivity)
    fun inject(activity: PeersActivity)
    fun inject(activity: PingActivity)
    fun inject(activity: SendPingActivity)
}
