package com.example.anggarin.ui.profil

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.ViewModelFactory
import com.example.anggarin.data.Result.Result
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.databinding.ActivityEditProfilBinding
import com.google.android.material.datepicker.MaterialDatePicker

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding
    private val editProfileViewModel: EditProfilViewModel by viewModels {
        ViewModelFactory(UserPreference(applicationContext))
    }

    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory(UserPreference(applicationContext))
    }

    private val REQUEST_PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe and set user data
        profileViewModel.userName.observe(this) { name ->
            binding.edtNamaEditProfile.text = name?.toEditable() ?: Editable.Factory.getInstance().newEditable("nama pengguna")
        }

        profileViewModel.userEmail.observe(this) { email ->
            binding.edtEmailEditProfile.text = email ?: "email@example.com"
        }

        // Observasi hasil update
        editProfileViewModel.updateProfileResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    // Handle success, misalnya menampilkan Toast atau update UI
                    Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    // Handle error
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }


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

            val fullName = binding.edtNamaEditProfile.text.toString()
            val dateOfBirth = binding.edtTgllahirEditProfile.text.toString()
            val phoneNumber = binding.edtNomorhpEditProfile.text.toString()

            // Validasi input
            if (fullName.isNotEmpty() && dateOfBirth.isNotEmpty() && phoneNumber.isNotEmpty()) {
                // Kirim URI gambar ke ViewModel
                val imageUri = binding.imgEditProfile.tag as? String  // Simpan tag dengan URI gambar
                editProfileViewModel.updateUserProfile(this, fullName, dateOfBirth, phoneNumber, imageUri)
                Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
//            startActivity(Intent(this, ProfilActivity::class.java))
//            @Suppress("DEPRECATION")
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
//            Toast.makeText(this, "data kamu tersimpan", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfilActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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
                binding.imgEditProfile.tag = imageUri.toString()  // Simpan URI sebagai tag untuk digunakan nanti
            } else {
                Toast.makeText(this, "Gambar tidak dipilih", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}