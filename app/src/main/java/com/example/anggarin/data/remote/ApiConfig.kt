package com.example.anggarin.data.remote

import android.content.Context
import android.util.Log
import com.example.anggarin.data.pref.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(context: Context): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create an AuthInterceptor to add token to requests
            val authInterceptor = Interceptor { chain ->
                val userPreference = UserPreference(context)
                val token = runBlocking { userPreference.getToken().first() } // Synchronously get token
                Log.d("Token", "Token is: $token")

                val request = if (!token.isNullOrEmpty()) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token") // Add token to Authorization header
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)  // Add the auth interceptor
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)  // Increase connection timeout
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)     // Increase read timeout
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://anggarin-api-service-1067208185659.asia-southeast2.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}