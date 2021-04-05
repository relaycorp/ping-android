package tech.relaycorp.ping.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import tech.relaycorp.ping.ui.common.MessageManager

open class BaseView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    protected val activity get() = context as BaseActivity
    protected val messageManager by lazy { MessageManager(activity) }
}
