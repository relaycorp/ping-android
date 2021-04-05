package tech.relaycorp.ping.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import javax.inject.Inject

class ReadFile
@Inject constructor(
    private val context: Context
) {

    fun read(uri: Uri) =
        context.contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        } ?: ByteArray(0)

    fun getFileName(uri: Uri): String {
        if (uri.scheme.equals("content")) {
            context.contentResolver
                .query(uri, null, null, null, null)
                ?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index != -1) {
                            return cursor.getString(index)
                        }
                    }
                }
        }

        return uri.path?.split("/")?.lastOrNull() ?: ""
    }
}
