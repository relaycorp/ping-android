package tech.relaycorp.ping.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import dagger.Module
import dagger.Provides
import tech.relaycorp.ping.data.database.AppDatabase
import javax.inject.Singleton

@Module
open class DataModule {

    protected open fun sharedPreferencesOpen(context: Context): SharedPreferences =
        context.getSharedPreferences("ping", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun sharedPreferences(context: Context) = sharedPreferencesOpen(context)

    @Provides
    @Singleton
    fun flowSharedPreferences(sharedPreferences: SharedPreferences) =
        FlowSharedPreferences(sharedPreferences)

    protected open fun appDatabaseOpen(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "ping").build()

    @Provides
    @Singleton
    fun appDatabase(context: Context) = appDatabaseOpen(context)

    @Provides
    @Singleton
    fun pingDao(db: AppDatabase) = db.pingDao()

    @Provides
    @Singleton
    fun publicPeerDao(db: AppDatabase) = db.publicPeerDao()
}
