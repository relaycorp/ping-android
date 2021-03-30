package tech.relaycorp.ping.ui.peers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialActionItem
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.android.synthetic.main.activity_main.list
import kotlinx.android.synthetic.main.activity_peers.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.relaycorp.ping.R
import tech.relaycorp.ping.common.di.ViewModelFactory
import tech.relaycorp.ping.domain.model.Peer
import tech.relaycorp.ping.ui.BaseActivity
import tech.relaycorp.ping.ui.common.getColorCompat
import tech.relaycorp.ping.ui.common.getColorFromAttr
import javax.inject.Inject

class PeersActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<PeersViewModel>

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PeersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_peers)
        setupNavigation()

        list.applyInsetter { type(navigationBars = true) { padding(bottom = true) } }
        addPeer.applyInsetter { type(navigationBars = true) { margin(bottom = true) } }
        list.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        addPeer.addActionItem(
            SpeedDialActionItem.Builder(R.id.publicPeer, R.drawable.ic_public)
                .setFabBackgroundColor(getColorFromAttr(R.attr.colorSurface))
                .setFabImageTintColor(getColorFromAttr(R.attr.colorOnSurface))
                .setLabelBackgroundColor(getColorCompat(android.R.color.transparent))
                .setLabel(R.string.peer_public)
                .create()
        )
        addPeer.addActionItem(
            SpeedDialActionItem.Builder(R.id.privatePeer, R.drawable.ic_private)
                .setFabBackgroundColor(getColorFromAttr(R.attr.colorSurface))
                .setFabImageTintColor(getColorCompat(R.color.gray_light))
                .setLabelColor(getColorCompat(R.color.gray_light))
                .setLabelBackgroundColor(getColorCompat(android.R.color.transparent))
                .setLabel(R.string.peer_private)
                .create()
        )

        viewModel
            .peers()
            .onEach(this::updateList)
            .launchIn(lifecycleScope)
    }

    private fun updateList(peers: List<Peer>) {
        list.withModels {
            peers.forEach { peer ->
                peerItemView {
                    id(peer.privateAddress)
                    item(peer)
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, PeersActivity::class.java)
    }
}
