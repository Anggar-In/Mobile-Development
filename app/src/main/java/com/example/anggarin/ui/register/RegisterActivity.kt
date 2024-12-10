package com.example.anggarin.ui.register

import RegisterViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.MainActivity
import com.example.anggarin.R
import com.example.anggarin.data.Result
import com.example.anggarin.databinding.ActivityRegisterBinding
import com.example.anggarin.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observer untuk hasil registrasi
        registerViewModel.registrationResult.observe(this) { result ->
            when (result) {
                is Result.Result.Success -> {
                    // Navigasi ke VerifikasiActivity dengan membawa email
                    val intent = Intent(this, VerifikasiActivity::class.java).apply {
                        putExtra("NAMA", binding.edtNamaRegister.text.toString().trim())
                        putExtra("EMAIL", binding.edtEmailRegister.text.toString().trim())
                    }
                    startActivity(intent)
                    finish()
                }
                is Result.Result.Error -> {
                    // Tampilkan pesan error
                    Log.e(this.toString(), "Register error ${result.error}")
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Setup event listeners
        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.imgBtnBackRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.txtLoginAkun.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun register() {
        val email = binding.edtEmailRegister.text.toString().trim()
        val nama = binding.edtNamaRegister.text.toString().trim()
        val password = binding.edtKatasandiRegister.text.toString().trim()

        // Panggil fungsi register di ViewModel
        registerViewModel.registerUser(this, nama, email, password)
    }
}