package com.example.anggarin.ui.register

import VerifikasiViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.data.Result
import com.example.anggarin.databinding.ActivityVerifikasiBinding
import com.example.anggarin.ui.login.LoginActivity

class VerifikasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifikasiBinding
    private val verifikasiViewModel: VerifikasiViewModel by viewModels()
    private var email: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil email dari intent sebelumnya (dari RegisterActivity)
        email = intent.getStringExtra("EMAIL") ?: ""

        // Update teks pada TextView
        binding.txtTextVerifikasi2.text = "Kode OTP akan dikirim ke $email"

        // Observer untuk hasil verifikasi OTP
        verifikasiViewModel.verificationResult.observe(this) { result ->
            when (result) {
                is Result.Result.Success -> {
                    Toast.makeText(this, "Verifikasi Otp berhasil", Toast.LENGTH_SHORT).show()
                    // Navigasi ke LoginActivity atau MainActivity setelah verifikasi berhasil
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                is Result.Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observer untuk hasil kirim ulang OTP
        verifikasiViewModel.resendOtpResult.observe(this) { result ->
            when (result) {
                is Result.Result.Success -> {
                    Toast.makeText(this, "Kode OTP berhasil dikirim ulang!", Toast.LENGTH_SHORT)
                        .show()
                }

                is Result.Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Tombol verifikasi OTP
        binding.btnVerifikasi.setOnClickListener {
            val otp = binding.edtInputVerifikasi.text.toString().trim()
            verifikasiViewModel.verifyOtp(this, email, otp)
        }

        // Tombol kirim ulang OTP
        binding.btnKirimUlang.setOnClickListener {
            if (email.isNotEmpty()) {
                verifikasiViewModel.resendOtp(this, email)
            } else {
                Toast.makeText(this, "Email tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivBtnBackVerifikasi.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}