package tech.relaycorp.ping.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.android.synthetic.main.common_app_bar.*
import tech.relaycorp.gateway.ui.common.MessageManager
import tech.relaycorp.ping.App
import tech.relaycorp.ping.R

abstract class BaseActivity : AppCompatActivity() {

    private val app get() = applicationContext as App
    val component by lazy { app.component.activityComponent() }

    protected val messageManager by lazy { MessageManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup edge-to-edge UI
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        toolbarTitle?.text = title
        appBar?.applyInsetter {
            type(statusBars = true) {
                padding(top = true)
            }
        }
    }

    protected fun setupNavigation(
        @DrawableRes icon: Int = R.drawable.ic_back,
        clickListener: (() -> Unit) = { finish() }
    ) {
        toolbar?.setNavigationIcon(icon)
        toolbar?.setNavigationOnClickListener { clickListener.invoke() }
    }
}
