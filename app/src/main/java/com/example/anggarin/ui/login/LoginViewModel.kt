package com.example.anggarin.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anggarin.data.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.LoginRequest
import com.example.anggarin.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _loginResult = MutableLiveData<Result.Result<LoginResponse>>()
    val loginResult: LiveData<Result.Result<LoginResponse>> = _loginResult


    fun loginUser(context: Context, email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _loginResult.postValue(Result.Result.Error("email dan password harus diisi"))
            return
        }
        viewModelScope.launch {
            try {
                // Call the API and handle the response
                val response = ApiConfig.getApiService(context).loginUser(loginRequest = LoginRequest(email = email, password = password))
                Log.i(TAG, "isSuccessful ${response.isSuccessful}")
                Log.i(TAG, "Response Code: ${response.code()}")
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    val loginResponse = response.body()

                    // Simpan token jika tersedia
                    val token = loginResponse?.token
                    val name = loginResponse?.user?.name
                    val email = loginResponse?.user?.email
                    if (!token.isNullOrEmpty() && !name.isNullOrEmpty() && !email.isNullOrEmpty()) {
                        // Save token, name, and email to UserPreference
                        pref.saveToken(token)
                        Log.d("LoginActivity", "Token setelah login: $token")
                        pref.saveUserData(name, email)
                        _loginResult.postValue(Result.Result.Success(loginResponse))
                    } else {
                        _loginResult.postValue(Result.Result.Error("data login tidak lengkap"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()

                    // Logika untuk pesan error
                    if (response.code() == 404) {
                        // 404 biasanya untuk "not found" (akun tidak terdaftar)
                        _loginResult.postValue(Result.Result.Error("Akun kamu belum terdaftar"))
                    } else {
                        // Error umum lainnya
                        _loginResult.postValue(Result.Result.Error("Login gagal: $errorBody"))
                    }
                }
            } catch (e: Exception) {
                _loginResult.postValue(Result.Result.Error("Error: ${e.localizedMessage}"))
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}