package com.example.anggarin.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.MainActivity
import com.example.anggarin.R
import com.example.anggarin.ViewModelFactory
import com.example.anggarin.data.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.databinding.ActivityLoginBinding
import com.example.anggarin.ui.profil.ProfilActivity
import com.example.anggarin.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(UserPreference(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observer untuk hasil registrasi
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Result.Success -> {
                    val intent = Intent(this, ProfilActivity::class.java).apply {
                        putExtra("EMAIL", binding.edtEmailLogin.text.toString().trim())
                    }
                    startActivity(intent)
                    finish()
                }
                is Result.Result.Error -> {
                    // Tampilkan pesan error
                    Log.e(this.toString(), "Login error ${result.error}")
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //aksi text register aja diklik
        binding.txtRegisterAkun.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //aksi tombol back diklik
        binding.imgBtnBackRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //aksi tombol login diklik
        binding.btnLogin.setOnClickListener {
            notaccount()
        }
    }

    private fun notaccount() {
        val email = binding.edtEmailLogin.text.toString().trim()
        val kataSandi = binding.edtKatasandiLogin.text.toString().trim()
        loginViewModel.loginUser(this, email, kataSandi)
    }
}