package com.example.anggarin.ui.investasi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityInvestasiBinding

class InvestasiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInvestasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBtnBackInvestasi.setOnClickListener(this)
        binding.btnMulaiInvestasi.setOnClickListener(this)
    }

    @Suppress("DEPRECATION")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_mulai_investasi -> {
                startActivity(Intent(this, InputInvestasiActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}