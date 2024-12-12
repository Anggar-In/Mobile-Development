package com.example.anggarin.ui.tabungan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityInputTabunganBinding
import com.google.android.material.datepicker.MaterialDatePicker

class InputTabunganActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInputTabunganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputTabunganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBtnBackInputtabungan.setOnClickListener(this)
        binding.btnSimpanTabungan.setOnClickListener(this)
        binding.btnKalenderawalTabungan.setOnClickListener(this)
        binding.btnKalendertargetTabungan.setOnClickListener(this)
    }

    @Suppress("DEPRECATION")
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_btn_back_inputtabungan -> {
                startActivity(Intent(this, TabunganActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }

            R.id.btn_simpan_tabungan -> {
                startActivity(Intent(this, TabunganActivity::class.java))
                Toast.makeText(this, "Tabungan berhasil dibuat", Toast.LENGTH_SHORT)
            }
            R.id.btn_kalenderawal_tabungan -> {
                tanggalAwalTabungan()
            }
            R.id.btn_kalendertarget_tabungan -> {
                tanggalTargetTabungan()
            }
        }
    }

    private fun tanggalAwalTabungan() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            binding.edtTglawalTabungan.setText(datePicker.headerText)
        }
    }

    private fun tanggalTargetTabungan() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            binding.edtTgltargetTabungan.setText(datePicker.headerText)
        }
    }
}