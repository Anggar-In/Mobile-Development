package com.example.anggarin.data.pref

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreference(context: Context) {


    private val dataStore = context.dataStore

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val EMAIL_KEY = stringPreferencesKey("user_email")
        private val IMAGE_KEY = stringPreferencesKey("image_profile")
    }

    // Fungsi untuk menyimpan URI gambar
    suspend fun saveImageProfile(uri: String) {
        dataStore.edit { preferences ->
            preferences[IMAGE_KEY] = uri
        }
    }

    // Fungsi untuk mengambil URI gambar
    fun getImageProfile(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[IMAGE_KEY]
        }
    }

    // Save token to DataStore
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
        Log.d("UserPreference", "Token disimpan: $token")
    }



    // Get token from DataStore
    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            val token = preferences[TOKEN_KEY]
            Log.d("UserPreference", "Token diambil: $token")
            token
        }
    }

    // Save user data (name and email) to DataStore
    suspend fun saveUserData(name: String, email: String) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[EMAIL_KEY] = email
        }
    }

    // Get user name from DataStore
    fun getUserName(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[NAME_KEY]
        }
    }

    // Get user email from DataStore
    fun getUserEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[EMAIL_KEY]
        }
    }

    // Clear all user data (token, name, and email) from DataStore
    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(NAME_KEY)
            preferences.remove(EMAIL_KEY)
        }
    }
}