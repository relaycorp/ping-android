package tech.relaycorp.ping.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.relaycorp.ping.data.database.dao.PingDao
import tech.relaycorp.ping.data.database.dao.PublicPeerDao
import tech.relaycorp.ping.data.database.entity.PingEntity
import tech.relaycorp.ping.data.database.entity.PublicPeerEntity

@Database(
    entities = [
        PingEntity::class,
        PublicPeerEntity::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pingDao(): PingDao
    abstract fun publicPeerDao(): PublicPeerDao
}
