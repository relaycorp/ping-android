package tech.relaycorp.ping.domain.model

enum class PeerType(val key: String) {
    Public("public"), Private("private");

    companion object {
        fun fromKey(key: String?) = key?.let { values().first { it.key == key } }
    }
}
