package com.example.anggarin.data.remote3

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig3 {
    companion object{
        fun getApiService3(): ApiService3 {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)  // Increase connection timeout
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)     // Increase read timeout
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://anggarin-api-invest-service-1067208185659.asia-southeast2.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService3::class.java)
        }
    }
}