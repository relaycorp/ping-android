package tech.relaycorp.ping.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class DummyItemView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 1 invisible px square
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY)
        )
    }
}
