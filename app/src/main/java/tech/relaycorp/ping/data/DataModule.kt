package tech.relaycorp.ping.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import tech.relaycorp.ping.data.database.AppDatabase

@Module
class DataModule {

    @Provides
    fun appDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "ping").build()

    @Provides
    fun pingDao(db: AppDatabase) = db.pingDao()

    @Provides
    fun publicPeerDao(db: AppDatabase) = db.publicPeerDao()
}
