package tech.relaycorp.ping.ui.common

import android.view.View
import android.widget.AdapterView

class SimpleItemSelectedListener(
    private val callback: (Int) -> Unit
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        callback(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
