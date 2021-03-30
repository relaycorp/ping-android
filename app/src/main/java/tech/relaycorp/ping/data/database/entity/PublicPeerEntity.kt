package tech.relaycorp.ping.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.relaycorp.ping.domain.model.Peer
import tech.relaycorp.ping.domain.model.PeerType

@Entity(tableName = "public_peer")
data class PublicPeerEntity(
    @PrimaryKey val privateAddress: String,
    val publicAddress: String,
    val deleted: Boolean = false
) {

    fun toModel() = Peer(
        privateAddress = privateAddress,
        alias = publicAddress,
        peerType = PeerType.Public
    )
}
