package com.example.anggarin.ui.profil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityEditProfilBinding
import com.google.android.material.datepicker.MaterialDatePicker

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding

    private val REQUEST_PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tombol untuk memilih tanggal lahir
        binding.btnKalender.setOnClickListener {
           tanggalLahir()
        }

        //tombol untuk kembali ke halaman sebelumnya
        binding.imgBtnBackEditProfile.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        //tombol untuk simpan edit profil
        binding.btnSimpanEditProfile.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            Toast.makeText(this, "data kamu tersimpan", Toast.LENGTH_SHORT).show()
        }



        //tombol masukkan foto profil
        binding.imgBtnEditProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*" // Membatasi hanya file gambar
            @Suppress("DEPRECATION")
            startActivityForResult(intent, REQUEST_PICK_IMAGE)
        }

    }

    private fun tanggalLahir() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            binding.edtTgllahirEditProfile.setText(datePicker.headerText)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            val imageUri = data?.data // Mendapatkan URI dari gambar yang dipilih
            if (imageUri != null) {
                // Menampilkan gambar yang dipilih di imgProfile
                binding.imgEditProfile.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "Gambar tidak dipilih", Toast.LENGTH_SHORT).show()
            }
        }
    }
}