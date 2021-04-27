package tech.relaycorp.ping.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Process.killProcess
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.activity_no_gateway.*
import tech.relaycorp.ping.R
import tech.relaycorp.ping.ui.BaseActivity

class NoGatewayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_gateway)
        setupNavigation(R.drawable.ic_close)

        download.setOnClickListener {
            openUrl(R.string.download_gateway)
        }
        downloadOther.setOnClickListener {
            openUrl(R.string.download_gateway_other)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Force app closure
        killProcess(android.os.Process.myPid());
    }

    private fun openUrl(@StringRes urlRes: Int) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(urlRes)))
        )
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, NoGatewayActivity::class.java)
    }
}
