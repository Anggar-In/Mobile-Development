package com.example.anggarin.ui.investasi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityInputInvestasiBinding

class InputInvestasiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInputInvestasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputInvestasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBtnBackInputinvestasi.setOnClickListener(this)
        binding.btnHitungInvestasi.setOnClickListener(this)
    }

    @Suppress("DEPRECATION")
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.img_btn_back_inputinvestasi -> {
                startActivity(Intent(this, InvestasiActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            R.id.btn_hitung_investasi -> {
                startActivity(Intent(this, InvestasiActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
    }
}