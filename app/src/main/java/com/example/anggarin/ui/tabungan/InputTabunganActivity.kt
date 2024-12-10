package com.example.anggarin.ui.tabungan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityInputTabunganBinding

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
        }
    }
}