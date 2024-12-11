package com.studio.anggarin.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.studio.anggarin.R
import com.studio.anggarin.ui.transaksi.CameraActivity
import com.studio.anggarin.ui.transaksi.PengeluaranActivity

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
    }
}