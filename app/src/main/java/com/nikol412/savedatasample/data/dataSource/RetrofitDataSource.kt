package com.nikol412.savedatasample.data.dataSource

import com.google.gson.Gson
import com.nikol412.savedatasample.data.response.WordResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitDataSource {
    @GET("{q}")
    suspend fun getWord(@Path("q") query: String): WordResponse
}

object Retrofit {
    fun getRetrofit(): RetrofitDataSource {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build().create(RetrofitDataSource::class.java)
    }
}
