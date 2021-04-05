package tech.relaycorp.ping.ui.peers

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.item_peer.view.*
import tech.relaycorp.ping.R
import tech.relaycorp.ping.domain.model.Peer
import tech.relaycorp.ping.ui.BaseView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PeerItemView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_peer, this)
    }

    @ModelProp
    fun setItem(item: Peer) {
        alias.text = item.alias
    }

    @CallbackProp
    fun setClickListener(listener: (() -> Unit)?) {
        setOnClickListener { listener?.invoke() }
    }
}
