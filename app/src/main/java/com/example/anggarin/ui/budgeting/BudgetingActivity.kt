package com.example.anggarin.ui.budgeting

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R

class BudgetingActivity : AppCompatActivity() {



    private lateinit var textSisaBudget: TextView
    private lateinit var textAngkaSisaBudget: TextView
    private lateinit var textTerpakai: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budgeting)
        textSisaBudget = findViewById(R.id.textSisaBudget)
        textAngkaSisaBudget = findViewById(R.id.textAngkaSisaBudget)
        textTerpakai = findViewById(R.id.textTerpakai)



        // Ambil data dari Intent
        val incomeMonth = intent.getIntExtra("INCOME_MONTH", 0)
        val budgetMonth = intent.getIntExtra("BUDGET_MONTH", 0)

        // Tampilkan data di TextView
        textSisaBudget.text = "Sisa Budget"
        textAngkaSisaBudget.text = "Rp.${incomeMonth - budgetMonth}" // Contoh penghitungan sisa budget
        textTerpakai.text = "Terpakai Rp.${budgetMonth}/Rp.${incomeMonth}"



        // Kamu juga bisa menggunakan progress bar jika ingin menampilkan progress sisa budget
    }
}