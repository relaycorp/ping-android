package tech.relaycorp.ping.ui.common.loading

import android.app.Activity
import android.app.AlertDialog
import android.view.WindowManager
import tech.relaycorp.ping.R
import tech.relaycorp.ping.common.di.PerActivity
import javax.inject.Inject

@PerActivity
class LoadingManager
@Inject constructor(
    private val activity: Activity
) {

    private var dialog: AlertDialog? = null

    fun show(messageRes: Int) {
        dialog?.let { hide() }
        dialog = AlertDialog.Builder(activity)
            .setView(
                LoadingView(activity).also {
                    it.setMessage(messageRes)
                }
            )
            .setCancelable(false)
            .show().also { dialog ->
                // Set the correct width for the dialog
                dialog.window?.let { window ->
                    window.attributes = WindowManager.LayoutParams().also {
                        it.copyFrom(window.attributes)
                        it.width =
                            activity.resources.getDimension(R.dimen.progress_dialog_width).toInt()
                    }
                }
            }
    }

    fun hide() {
        dialog?.dismiss()
        dialog = null
    }
}
