package tech.relaycorp.ping.data.database

import androidx.room.TypeConverter
import tech.relaycorp.ping.domain.model.PeerType
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class Converters {
    @TypeConverter
    fun toPeerType(value: String?) = PeerType.fromKey(value)

    @TypeConverter
    fun fromPeerType(peerType: PeerType?) = peerType?.key

    @TypeConverter
    fun toZonedDateTime(value: Long?): ZonedDateTime? = value?.let {
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault())
    }

    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime?) = value?.toInstant()?.toEpochMilli()
}
