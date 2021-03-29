package tech.relaycorp.ping.ui.common

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.use
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_field.view.*
import tech.relaycorp.ping.R
import tech.relaycorp.ping.ui.BaseView

class FieldView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {

    var label: String?
        get() = labelText.text.toString()
        set(value) {
            labelText.text = value
        }

    var value: String
        get() = valueText.text.toString()
        set(value) {
            valueText.text = value
        }

    var copyEnabled: Boolean
        get() = copy.isVisible
        set(value) {
            copy.isVisible = value
        }

    private val clipboard by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    init {
        inflate(context, R.layout.view_field, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.Field, 0, 0).use {
            label = it.getString(R.styleable.Field_label) ?: ""
            copyEnabled = it.getBoolean(R.styleable.Field_copyEnabled, false)
        }

        copy.setOnClickListener {
            clipboard.setPrimaryClip(ClipData.newPlainText(label, value))
            messageManager.showMessage(R.string.copy_confirm)
        }
    }
}
