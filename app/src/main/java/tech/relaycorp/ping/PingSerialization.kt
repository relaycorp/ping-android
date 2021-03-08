package tech.relaycorp.ping

import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import tech.relaycorp.relaynet.messages.payloads.ServiceMessage

internal fun makePingServiceMessage(
    pingId: String,
    pda: ByteArray,
    pdaChain: List<ByteArray>
): ServiceMessage {
    val pingJSON = JSONObject()
    pingJSON.put("id", pingId)
    pingJSON.put("pda", base64Encode(pda))
    pingJSON.put("pda_chain", JSONArray(pdaChain.map { base64Encode(it) }))
    val pingJSONString = pingJSON.toString()
    return ServiceMessage(
        "application/vnd.relaynet.ping-v1.ping",
        pingJSONString.toByteArray()
    )
}

private fun base64Encode(input: ByteArray): String =
    Base64.encodeToString(input, Base64.DEFAULT)

internal fun extractPingIdFromPongMessage(pongMessageSerialized: ByteArray): String {
//    return pongMessageSerialized.toString(Charset.defaultCharset())
    return "We have to fix https://github.com/relaycorp/relaynet-endpoint-android/issues/42"
}
