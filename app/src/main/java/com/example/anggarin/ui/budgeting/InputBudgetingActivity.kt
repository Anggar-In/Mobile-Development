package com.example.anggarin.ui.budgeting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.anggarin.R
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.response.BudgetRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InputBudgetingActivity : AppCompatActivity() {

    private lateinit var incomeEditText: EditText
    private lateinit var budgetEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_budgeting)

        // Inisialisasi EditText dan Button
        incomeEditText = findViewById(R.id.etIncomeMonth)
        budgetEditText = findViewById(R.id.etBudgetMonth)
        saveButton = findViewById(R.id.btnSaveBudget)

        // Tombol save untuk menyimpan data budgeting
        saveButton.setOnClickListener {
            val incomeMonth = incomeEditText.text.toString().toIntOrNull()
            val budgetMonth = budgetEditText.text.toString().toIntOrNull()

            if (incomeMonth != null && budgetMonth != null) {
                saveBudget(incomeMonth, budgetMonth)
            } else {
                Toast.makeText(this, "Mohon isi semua field dengan benar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBudget(incomeMonth: Int, budgetMonth: Int) {
        // Ambil token dari UserPreference
        val userPreference = UserPreference(applicationContext)

        lifecycleScope.launch {
            val token = userPreference.getToken().first()
            if (!token.isNullOrEmpty()) {
                val apiService = ApiConfig.getApiService(this@InputBudgetingActivity)

                // Membuat request data budget
                val budgetRequest = BudgetRequest(incomeMonth, budgetMonth)

                try {
                    // Panggil API untuk menyimpan data budgeting
                    val response = apiService.postBudget("Bearer$token", budgetRequest)

                    // Menampilkan hasil sukses
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(this@InputBudgetingActivity, "Budget berhasil disimpan", Toast.LENGTH_LONG).show()

                        // Menyimpan data dan melanjutkan ke activity berikutnya
                        val intent = Intent(this@InputBudgetingActivity, BudgetingActivity::class.java).apply {
                            putExtra("INCOME_MONTH", incomeMonth)  // Kirim data incomeMonth
                            putExtra("BUDGET_MONTH", budgetMonth)  // Kirim data budgetMonth
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@InputBudgetingActivity, "Gagal menyimpan budget", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@InputBudgetingActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } else {
                // Menangani jika token tidak ditemukan
                Toast.makeText(this@InputBudgetingActivity, "Token tidak valid. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
