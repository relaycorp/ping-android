package tech.relaycorp.ping.test

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import tech.relaycorp.ping.data.DataModule

@Module
class TestDataModule : DataModule() {

    override fun sharedPreferencesOpen(context: Context): SharedPreferences =
        context.getSharedPreferences("ping_test", Context.MODE_PRIVATE)

    override fun appDatabaseOpen(context: Context) = AppTestProvider.database
}
