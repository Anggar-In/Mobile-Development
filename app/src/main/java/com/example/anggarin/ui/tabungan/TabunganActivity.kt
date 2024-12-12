package com.example.anggarin.ui.tabungan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.databinding.ActivityTabunganBinding

class TabunganActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTabunganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabunganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabTambahTabungan.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fab_tambah_tabungan -> {
                startActivity(Intent(this, InputTabunganActivity::class.java))
                @Suppress("DEPRECATION")
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}