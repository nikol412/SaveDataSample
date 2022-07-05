package com.nikol412.savedatasample

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.nikol412.savedatasample.data.dataSource.*
import com.nikol412.savedatasample.data.roomDb.AppDatabase
import com.nikol412.savedatasample.data.repository.DictionaryRepository
import com.nikol412.savedatasample.data.repository.DictionaryRepositoryImpl
import com.nikol412.savedatasample.utils.dataStore
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class SaveApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SaveApp)
            modules(databaseModule, module, viewModelModule)
            androidLogger(Level.DEBUG)
        }
    }
}

private val module = module {
    single { Dispatchers.IO }
    single { Retrofit.getRetrofit() }
    factory { Gson() }
    factory { GsonWordConverted(get()) }
    single { androidApplication().dataStore }
    single {
        androidApplication().getSharedPreferences(
            SharedPreferencesDataSource.SHARED_PREF_DEFAULT,
            Context.MODE_PRIVATE
        )
    }
    single { SharedPreferencesDataSource(get(), get()) }
    single { PrefDataStoreDataSource(get(), get()) }
    single { RoomDataSource(get(), get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get(), get(), get(), get(), get()) }
}

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "save-data-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().wordDao() }
}