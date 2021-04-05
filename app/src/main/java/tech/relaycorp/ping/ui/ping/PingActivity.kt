package tech.relaycorp.ping.ui.ping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_ping.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.relaycorp.ping.R
import tech.relaycorp.ping.common.di.ViewModelFactory
import tech.relaycorp.ping.ui.BaseActivity
import tech.relaycorp.ping.ui.common.DateTimeFormat
import javax.inject.Inject

class PingActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<PingViewModel>

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PingViewModel::class.java)
    }

    private val pingId by lazy { intent.getStringExtra(PING_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_ping)
        setupNavigation()

        viewModel
            .ping()
            .onEach {
                recipient.text = it.peer.alias
                sentAt.text = DateTimeFormat.format(it.sentAt)
                pingIdField.value = it.pingId
                expiresAtField.value = DateTimeFormat.format(it.expiresAt)
                pongReceivedField.isVisible = it.pongReceivedAt != null
                pongReceivedField.value = DateTimeFormat.format(it.pongReceivedAt)
            }
            .launchIn(lifecycleScope)

        pingId?.let { viewModel.pingIdReceived(it) } ?: finish()
    }

    companion object {
        private const val PING_ID = "pingId"

        fun getIntent(context: Context, pingId: String) =
            Intent(context, PingActivity::class.java)
                .putExtra(PING_ID, pingId)
    }
}
