package com.example.anggarin.ui.profil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.MainActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra("NAMA")
        val email = intent.getStringExtra("EMAIL")

        binding.txtNameProfile.text = nama
        binding.txtEmailProfile.text = email

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
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Keluar")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Iya") { _, _ ->
                // Aksi jika memilih "Iya"
                startActivity(Intent(this, MainActivity::class.java))
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                // Aksi jika memilih "Tidak"
                dialog.dismiss()
            }
            .create()
            .show()
    }

    //menampilkan gambar profile dari edit profile

}