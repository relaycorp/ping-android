package tech.relaycorp.ping.data

import android.content.Context
import android.net.Uri
import javax.inject.Inject

class ReadFile
@Inject constructor(
    private val context: Context
) {

    fun read(uri: Uri) =
        context.contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        } ?: ByteArray(0)
}
