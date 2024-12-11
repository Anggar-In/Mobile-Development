package com.example.anggarin.ui.profil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.anggarin.R
import com.example.anggarin.ViewModelFactory
import com.example.anggarin.data.Result.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.databinding.ActivityProfilBinding
import com.example.anggarin.ui.login.LoginActivity

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory(UserPreference(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe and set user data
        profileViewModel.userName.observe(this) { name ->
            binding.txtNameProfile.text = name ?: "Nama Pengguna"
        }

        profileViewModel.userEmail.observe(this) { email ->
            binding.txtEmailProfile.text = email ?: "email@example.com"
        }

        // Observasi LiveData untuk gambar profil
        profileViewModel.imageProfile.observe(this) { imageUri ->
            if (imageUri != null) {
                // Menampilkan gambar menggunakan Glide
                Glide.with(this)
                    .load(imageUri)
                    .circleCrop()  // Menampilkan gambar sebagai lingkaran
                    .into(binding.imgProfile)  // Ganti dengan ImageView yang sesuai di layout
            } else {
                // Menampilkan gambar default jika URI kosong
                binding.imgProfile.setImageResource(R.drawable.img_profile)  // Ganti dengan gambar default
            }
        }

        // Observasi hasil logout
        profileViewModel.logoutResult.observe(this) { result ->
            when (result) {
                is Result.Success -> navigateToLogin()
                is Result.Error -> showError(result.error ?: "Terjadi kesalahan")
            }
        }


        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfilActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnKeluar.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah kamu yakin mau keluar?")
            .setCancelable(false)
            .setPositiveButton("Iya") { dialog, id ->
                profileViewModel.logoutUser(this)
            }
            .setNegativeButton("Tidak") { dialog, id ->
                dialog.dismiss()  // Tutup dialog jika pengguna pilih "Tidak"
            }

        // Tampilkan dialog
        val alert = builder.create()
        alert.show()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
