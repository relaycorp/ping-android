package tech.relaycorp.ping.ui.common.loading

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_loading.view.*
import tech.relaycorp.ping.R

class LoadingView(context: Context) : FrameLayout(context) {

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        View.inflate(context, R.layout.view_loading, this)
    }

    fun setMessage(messageRes: Int) = loadingMessage.setText(messageRes)
}
