package tech.relaycorp.ping.ui.common

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(
    private val textChanged: (String) -> Unit
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChanged(s?.toString() ?: "")
    }

    override fun afterTextChanged(s: Editable?) {
    }
}
