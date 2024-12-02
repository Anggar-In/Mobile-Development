package com.studio.anggarin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity) // Layout yang sudah Anda buat sebelumnya
        val budgetingButton: ImageView = findViewById(R.id.img_add_budget)
        val addButton: ImageView = findViewById(R.id.img_add_plus)

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