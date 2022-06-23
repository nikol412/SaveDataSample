package com.nikol412.savedatasample

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.nikol412.savedatasample.data.dataSource.Retrofit
import com.nikol412.savedatasample.data.dataSource.SharedPreferencesDataSource
import com.nikol412.savedatasample.data.repository.DictionaryRepository
import com.nikol412.savedatasample.data.repository.DictionaryRepositoryImpl
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
            modules(module, viewModelModule)
            androidLogger(Level.DEBUG)
        }
    }
}

private val module = module {
    single { Dispatchers.IO }
    single { Retrofit.getRetrofit() }
    single { Gson() }
    single {
        androidApplication().getSharedPreferences(
            SharedPreferencesDataSource.SHARED_PREF_DEFAULT,
            Context.MODE_PRIVATE
        )
    }
    single { SharedPreferencesDataSource(get(), get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get(), get(), get()) }
}

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}