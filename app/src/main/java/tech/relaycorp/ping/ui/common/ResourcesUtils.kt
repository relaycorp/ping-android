package tech.relaycorp.ping.ui.common

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

fun Context.getColorCompat(@ColorRes colorRes: Int) =
    ResourcesCompat.getColor(resources, colorRes, theme)


fun Context.getColorFromAttr(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    val actionBarSizeAttrs = intArrayOf(attr)
    val typedArray = obtainStyledAttributes(typedValue.data, actionBarSizeAttrs)
    val value = typedArray.getColor(0, 0)
    typedArray.recycle()
    return value
}

