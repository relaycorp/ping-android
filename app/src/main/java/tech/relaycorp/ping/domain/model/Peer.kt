package tech.relaycorp.ping.domain.model

data class Peer(
    val privateAddress: String,
    val alias: String,
    val peerType: PeerType
)
