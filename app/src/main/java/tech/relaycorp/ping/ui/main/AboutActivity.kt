package tech.relaycorp.ping.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.mikepenz.aboutlibraries.LibsBuilder
import kotlinx.android.synthetic.main.activity_about.*
import tech.relaycorp.ping.BuildConfig
import tech.relaycorp.ping.R
import tech.relaycorp.ping.ui.BaseActivity

class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setupNavigation()

        version.text = getString(
            R.string.about_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE.toString()
        )
        learnMore.setOnClickListener { openKnowMore() }
        libraries.setOnClickListener { openLicenses() }
    }

    private fun openKnowMore() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.relaynet_website))))
    }

    private fun openLicenses() {
        LibsBuilder()
            .withActivityTitle(getString(R.string.about_licenses))
            .withAboutIconShown(false)
            .withVersionShown(false)
            .withOwnLibsActivityClass(LicensesActivity::class.java)
            .withEdgeToEdge(true)
            .withFields(R.string::class.java.fields)
            .start(this)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, AboutActivity::class.java)
    }
}
