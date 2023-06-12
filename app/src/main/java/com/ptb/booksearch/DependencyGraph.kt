package com.ptb.booksearch

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ptb.booksearch.network.BookApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File


//Simple dependency graph, in a real app this would be Dagger

const val apiBaseUrl = "http://openlibrary.org/"

object DependencyGraph {
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    lateinit var bookApi: BookApi

    @OptIn(ExperimentalSerializationApi::class)
    fun provide(context: Context) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .cache(
                Cache(
                    File(context.cacheDir, "http_cache"),
                    50L * 1024L * 1024L // 50 MiB
                )
            )
            .build()

        val json = Json { ignoreUnknownKeys = true }
        retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

        bookApi = retrofit.create(BookApi::class.java)
    }
}