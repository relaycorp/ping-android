package tech.relaycorp.ping

import android.util.Base64
import org.json.JSONArray
import java.nio.charset.Charset
import org.json.JSONObject

internal fun serializePing(
    pingId: String,
    pda: ByteArray,
    pdaChain: List<ByteArray>
): ByteArray {
    val pingJSON = JSONObject()
    pingJSON.put("id", pingId)
    pingJSON.put("pda", base64Encode(pda))

    val pdaChainJSON = JSONArray()
    pdaChain.forEach { pdaChainJSON.put(it) }

    return pingJSON.toString().toByteArray()
}

private fun base64Encode(input: ByteArray): String =
    Base64.encodeToString(input, Base64.DEFAULT)

internal fun extractPingIdFromPongMessage(pongMessageSerialized: ByteArray): String =
    pongMessageSerialized.toString(Charset.defaultCharset())
