package tech.relaycorp.ping.awala

import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import javax.inject.Inject

class PingSerialization
@Inject constructor() {

    fun serialize(
        pingId: String,
        pda: ByteArray,
        pdaChain: List<ByteArray>
    ): ByteArray {
        val pingJSON = JSONObject()
        pingJSON.put("id", pingId)
        pingJSON.put("pda", base64Encode(pda))
        pingJSON.put("pda_chain", JSONArray(pdaChain.map { base64Encode(it) }))
        val pingJSONString = pingJSON.toString()
        return pingJSONString.toByteArray()
    }

    private fun base64Encode(input: ByteArray): String =
        Base64.encodeToString(input, Base64.DEFAULT)

    fun extractPingIdFromPong(pongMessageSerialized: ByteArray): String {
        return pongMessageSerialized.toString(Charset.defaultCharset())
    }
}
