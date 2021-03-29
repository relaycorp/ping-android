package tech.relaycorp.ping.ui.main

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.item_ping.view.*
import tech.relaycorp.ping.R
import tech.relaycorp.ping.domain.model.Ping
import tech.relaycorp.ping.ui.BaseView
import tech.relaycorp.ping.ui.common.DateTimeFormat

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PingItemView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_ping, this)
    }

    @ModelProp
    fun setItem(item: Ping) {
        recipient.text = item.peer.alias
        sentAt.text = DateTimeFormat.format(item.sentAt)

        state.setImageResource(
            when {
                !item.pongReceived -> R.drawable.ic_check
                else -> R.drawable.ic_double_check
            }
        )
        state.contentDescription = resources.getString(
            when {
                !item.pongReceived -> R.string.ping_sent
                else -> R.string.ping_sent_and_replied
            }
        )
    }
}
