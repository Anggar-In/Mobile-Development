package com.example.anggarin.ui.profil

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anggarin.data.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.LogoutResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {

    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> = _userName

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> = _userEmail

    private val _imageProfile = MutableLiveData<String?>()
    val imageProfile: LiveData<String?> = _imageProfile

    init {
        // Collect user data when ViewModel is created
        collectUserData()
    }

    private fun collectUserData() {
        viewModelScope.launch {
            // Collect username
            _userName.postValue(pref.getUserName().first())

            // Collect user email
            _userEmail.postValue(pref.getUserEmail().first())
        }
    }

    private val _logoutResult = MutableLiveData<Result.Result<LogoutResponse>>()
    val logoutResult: LiveData<Result.Result<LogoutResponse>> = _logoutResult

    fun logoutUser(context: Context) {
        viewModelScope.launch {
            try {
                val token = pref.getToken().first() // Ambil token dari UserPreference
                val response = ApiConfig.getApiService(context).logoutUser("Bearer $token") // Kirim token ke API
                if (response.isSuccessful) {
                    val logoutResponse = response.body()
                    _logoutResult.postValue(Result.Result.Success(logoutResponse!!))
                    pref.clearUserData() // Hapus nama, email, token user
                } else {
                    _logoutResult.postValue(Result.Result.Error("Logout gagal: ${response.message()}"))
                }
            } catch (e: Exception) {
                _logoutResult.postValue(Result.Result.Error("Error: ${e.localizedMessage}"))
            }
        }
    }
}