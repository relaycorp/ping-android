package tech.relaycorp.ping.ui.peers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_add_public_peer.*
import kotlinx.android.synthetic.main.common_app_bar.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.relaycorp.ping.R
import tech.relaycorp.ping.common.di.ViewModelFactory
import tech.relaycorp.ping.ui.BaseActivity
import tech.relaycorp.ping.ui.common.SimpleTextWatcher
import javax.inject.Inject

class AddPublicPeerActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AddPublicPeerViewModel>

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AddPublicPeerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_add_public_peer)
        setupNavigation(R.drawable.ic_close)

        toolbar.inflateMenu(R.menu.add_peer)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> viewModel.saveClicked()
            }
            true
        }

        addressEdit.addTextChangedListener(SimpleTextWatcher {
            viewModel.aliasChanged(it)
        })
        certificateButton.setOnClickListener {
            openFileDialog()
        }
        certificateClear.setOnClickListener {
            viewModel.removeCertificateClicked()
        }

        viewModel
            .hasCertificate()
            .onEach {
                certificateButton.isVisible = !it
                certificateName.isVisible = it
                certificateClear.isVisible = it
            }
            .launchIn(lifecycleScope)

        viewModel
            .saveEnabled()
            .onEach {
                toolbar.menu.findItem(R.id.save).isEnabled = it
            }
            .launchIn(lifecycleScope)

        viewModel
            .errors()
            .onEach {
                messageManager.showError(
                    when (it) {
                        AddPublicPeerViewModel.Error.Save -> R.string.peer_add_error
                    }
                )
            }
            .launchIn(lifecycleScope)

        viewModel
            .finish()
            .onEach { finish() }
            .launchIn(lifecycleScope)

        results
            .onEach {
                if (it.requestCode == PICK_CERTIFICATE && it.resultCode == Activity.RESULT_OK) {
                    it.data?.data?.let { uri -> viewModel.certificatePicked(uri) }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun openFileDialog() {
        startActivityForResult(
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "*/*"
            },
            PICK_CERTIFICATE
        )
    }

    companion object {
        private const val PICK_CERTIFICATE = 11

        fun getIntent(context: Context) = Intent(context, AddPublicPeerActivity::class.java)
    }
}
