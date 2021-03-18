package tech.relaycorp.ping.ui.main

import android.os.Bundle
import tech.relaycorp.ping.R
import tech.relaycorp.ping.ui.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)
    }
}
