package com.example.anggarin.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.ui.budgeting.BudgetingActivity
import com.example.anggarin.ui.budgeting.InputBudgetingActivity
import com.example.anggarin.ui.investasi.InvestasiActivity
import com.example.anggarin.ui.profil.ProfilActivity
import com.example.anggarin.ui.transaksi.AudioActivity
import com.example.anggarin.ui.transaksi.CameraActivity
import com.example.anggarin.ui.transaksi.PengeluaranActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // Menemukan elemen UI
        val microphoneButton: ImageView = findViewById(R.id.img_microphone)
        val budgetingButton: ImageView = findViewById(R.id.img_add_budget)
        val addButton: ImageView = findViewById(R.id.img_add_plus)
        val cameraButton: ImageView = findViewById(R.id.img_camera)
        val profilButton: ImageView = findViewById(R.id.btn_pindah_profil)
        val investasiButton: ImageView = findViewById(R.id.img_btn_investasi)

        microphoneButton.setOnClickListener{
            val intent = Intent(this, AudioActivity::class.java)
            startActivity(intent)
        }



        cameraButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, PengeluaranActivity::class.java)
            startActivity(intent)
        }

        budgetingButton.setOnClickListener {
            val intent = Intent(this, InputBudgetingActivity::class.java)
            startActivity(intent)
        }

        profilButton.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        investasiButton.setOnClickListener {
            startActivity(Intent(this, InvestasiActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}