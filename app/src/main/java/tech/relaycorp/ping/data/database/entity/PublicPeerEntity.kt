package tech.relaycorp.ping.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "public_peer")
data class PublicPeerEntity(
    @PrimaryKey val privateAddress: String,
    val publicAddress: String,
    val deleted: Boolean = false
)
