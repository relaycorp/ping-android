package tech.relaycorp.ping.ui.main

import android.os.Bundle
import com.mikepenz.aboutlibraries.ui.LibsActivity
import tech.relaycorp.ping.R

class LicensesActivity : LibsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.elevation = 0f
    }
}
