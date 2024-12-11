package com.example.anggarin.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.ui.budgeting.BudgetingActivity
import com.example.anggarin.ui.investasi.InvestasiActivity
import com.example.anggarin.ui.profil.ProfilActivity
import com.example.anggarin.ui.transaksi.CameraActivity
import com.example.anggarin.ui.transaksi.PengeluaranActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saldoPengeluaranTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Layout yang sudah Anda buat sebelumnya

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("FinanceApp", MODE_PRIVATE)

        // Menemukan elemen UI
        saldoPengeluaranTextView = findViewById(R.id.saldoPengeluaran)
        val budgetingButton: ImageView = findViewById(R.id.img_add_budget)
        val addButton: ImageView = findViewById(R.id.img_add_plus)
        val cameraButton: ImageView = findViewById(R.id.img_camera)
        val profilButton: ImageView = findViewById(R.id.btn_pindah_profil)
        val investasiButton: ImageView = findViewById(R.id.img_btn_investasi)


        // Mengambil total pengeluaran dan menampilkannya
        val totalPengeluaran = sharedPreferences.getFloat("TotalPengeluaran", 0f)
        saldoPengeluaranTextView.text = "Rp. $totalPengeluaran"

        cameraButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, PengeluaranActivity::class.java)
            startActivity(intent)
        }

        budgetingButton.setOnClickListener {
            val intent = Intent(this, BudgetingActivity::class.java)
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