package com.example.anggarin.ui.profil

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.databinding.ActivityEditProfilBinding
import com.google.android.material.datepicker.MaterialDatePicker

class EditProfilActivity : AppCompatActivity() {

    private lateinit var activityEditProfilBinding: ActivityEditProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityEditProfilBinding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(activityEditProfilBinding.root)

        activityEditProfilBinding.btnKalender.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                activityEditProfilBinding.edtTgllahirEditProfile.setText(datePicker.headerText)
            }
        }
    }
}