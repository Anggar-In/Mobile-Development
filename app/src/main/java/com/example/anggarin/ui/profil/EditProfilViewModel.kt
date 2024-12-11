package com.example.anggarin.ui.profil

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anggarin.data.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.UpdateProfileRequest
import com.example.anggarin.data.response.UpdateProfileResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditProfilViewModel(private val pref: UserPreference) : ViewModel() {
    private val _updateProfileResult = MutableLiveData<Result.Result<UpdateProfileResponse>>()
    val updateProfileResult: LiveData<Result.Result<UpdateProfileResponse>> = _updateProfileResult

    fun updateUserProfile(context: Context, fullName: String, dateOfBirth: String, phoneNumber: String, imageUri: String?) {
        viewModelScope.launch {
            try {
                // Ambil token dari Shared Preferences atau DataStore
                val token = pref.getToken().first() ?: ""

                // Membuat objek request
                val request = UpdateProfileRequest(
                    fullName = fullName,
                    dateOfBirth = dateOfBirth,
                    phoneNumber = phoneNumber
                )

                // Panggil API untuk update profil
                val response = ApiConfig.getApiService(context).updateProfile("Bearer $token", request)

                if (response.isSuccessful) {
                    imageUri?.let {
                        pref.saveImageProfile(it)  // Simpan URI gambar ke DataStore
                    }
                    _updateProfileResult.postValue(Result.Result.Success(response.body()!!))
                } else {
                    val errorBody = response.errorBody()?.string()
                    _updateProfileResult.postValue(Result.Result.Error("Gagal mengupdate profil: $errorBody"))
                }
            } catch (e: Exception) {
                _updateProfileResult.postValue(Result.Result.Error("Terjadi kesalahan: ${e.localizedMessage}"))
            }
        }
    }
}
