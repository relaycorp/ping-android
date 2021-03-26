package tech.relaycorp.ping.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import tech.relaycorp.ping.data.database.AppDatabase

@Module
class DataModule {

    fun appDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "ping").build()

    fun pingDao(db: AppDatabase) = db.pingDao()
}
