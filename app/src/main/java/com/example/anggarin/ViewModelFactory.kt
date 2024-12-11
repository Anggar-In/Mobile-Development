package com.example.anggarin

import RegisterViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.ui.login.LoginViewModel
import com.example.anggarin.ui.profil.EditProfilViewModel
import com.example.anggarin.ui.profil.ProfileViewModel

class ViewModelFactory(private val userPreference: UserPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userPreference) as T
            }
            modelClass.isAssignableFrom(EditProfilViewModel::class.java) -> {
                EditProfilViewModel(userPreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}