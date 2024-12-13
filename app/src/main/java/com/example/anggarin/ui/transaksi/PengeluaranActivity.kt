package com.example.anggarin.ui.transaksi

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.anggarin.R
import com.example.anggarin.data.dummyKategori.dummyCategories
import com.example.anggarin.data.pref.UserPreference
import com.example.anggarin.data.remote.ApiConfig
import com.example.anggarin.data.remote.ApiService
import com.example.anggarin.data.response.Category
import com.example.anggarin.data.response.Expense
import com.example.anggarin.data.response.ExpenseRequest
import com.example.anggarin.data.response.ExpenseResponse
import com.example.anggarin.ui.login.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class PengeluaranActivity : AppCompatActivity() {





    private lateinit var editTextTempatBeli: EditText
    private lateinit var editTextItem: EditText

    private lateinit var kategori1: FrameLayout
    private lateinit var kategori2: FrameLayout
    private lateinit var kategori3: FrameLayout
    private lateinit var kategori1Name: TextView
    private lateinit var kategori2Name: TextView
    private lateinit var kategori3Name: TextView
    private var selectedCategory: String? = null // Menyimpan kategori yang dipilih

    @SuppressLint("MissingInflatedId", "CutPasteId")
    private lateinit var editTextNominal: EditText
    private lateinit var buttonSelectDate: ImageView
    private lateinit var textViewDate: TextView
    private lateinit var buttonSaveTransaction: Button

    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pengeluaran)



        // Ambil data dari Intent
        val company = intent.getStringExtra("COMPANY")
        val total = intent.getStringExtra("TOTAL")
        val items = intent.getStringExtra("ITEMS")

        // Setel data ke UI
        val etNominal: EditText = findViewById(R.id.etNominal)
        etNominal.setText(total)  // Setel total ke EditText

        val etTempatBeli: EditText = findViewById(R.id.etTempatBeli)
        etTempatBeli.setText(company)  // Setel company ke EditText

        val etItem: EditText = findViewById(R.id.etItem)
        etItem.setText(items)  // Setel items ke EditText

        // Inisialisasi elemen UI
        editTextNominal = findViewById(R.id.etNominal)
        buttonSelectDate = findViewById(R.id.btnSelectDate)
        textViewDate = findViewById(R.id.tvDate)
        buttonSaveTransaction = findViewById(R.id.btnSaveTransaction)
        editTextTempatBeli = findViewById(R.id.etTempatBeli)
        editTextItem = findViewById(R.id.etItem)

        kategori1 = findViewById(R.id.kategori1)
        kategori2 = findViewById(R.id.kategori2)
        kategori3 = findViewById(R.id.kategori3)

        kategori1Name = findViewById(R.id.kategori1Name)
        kategori2Name = findViewById(R.id.kategori2Name)
        kategori3Name = findViewById(R.id.kategori3Name)

        // Menambahkan listener untuk kategori
        kategori1.setOnClickListener {
            selectedCategory = kategori1Name.text.toString()
            updateCategorySelection()
        }
        kategori2.setOnClickListener {
            selectedCategory = kategori2Name.text.toString()
            updateCategorySelection()
        }
        kategori3.setOnClickListener {
            selectedCategory = kategori3Name.text.toString()
            updateCategorySelection()
        }

        // Menampilkan DatePickerDialog saat memilih tanggal
        buttonSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    textViewDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        buttonSaveTransaction.setOnClickListener {
            val userPreference = UserPreference(applicationContext)

            lifecycleScope.launch {

                val token = userPreference.getToken().first()
                if (!token.isNullOrEmpty()) {
                    val apiService = ApiConfig.getApiService(applicationContext)

                    // Ambil input dari pengguna
                    val expenseAmount = editTextNominal.text.toString().toIntOrNull() ?: 0
                    val expenseDate = selectedDate ?: "" // Pastikan user memilih tanggal
                    val store = editTextTempatBeli.text.toString()
                    val items = editTextItem.text.toString().split(",").map { it.trim() } // Pisahkan item dengan koma

                    // Pastikan kategori telah dipilih
                    val category = selectedCategory ?: "Kategori tidak dipilih"

                    if (expenseAmount <= 0 || expenseDate.isEmpty() || store.isEmpty() || items.isEmpty() || category == "Kategori tidak dipilih") {
                        Toast.makeText(
                            this@PengeluaranActivity,
                            "Harap lengkapi semua data dan pilih kategori",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    try {
                        val category_id = when (selectedCategory) {
                            kategori1Name.text.toString() -> "Makanan"
                            kategori2Name.text.toString() -> "Transportasi"
                            kategori3Name.text.toString() -> "Lainnya"
                            else -> null // Jika kategori tidak dipilih
                        }

                        Log.d("PengeluaranActivity", "Category ID: $category_id")


                        if (category_id == null) {
                            Toast.makeText(
                                this@PengeluaranActivity,
                                "Harap pilih kategori",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }

                        val expenseRequest = ExpenseRequest(
                            expenseAmount = expenseAmount,
                            expenseDate = expenseDate,
                            store = store,
                            items = items,
                        )
                        val response = apiService.postExpense("Bearer$token",
                            category_id.toString(), expenseRequest)
                        if (response.isSuccessful && response.body() != null) {
                            Toast.makeText(
                                this@PengeluaranActivity,
                                "Pengeluaran berhasil disimpan",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Tutup activity setelah berhasil menyimpan
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.e("PengeluaranActivity", "Error: $errorBody")
                            Toast.makeText(
                                this@PengeluaranActivity,
                                "Gagal menyimpan pengeluaran: $errorBody",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@PengeluaranActivity,
                            "Terjadi kesalahan: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@PengeluaranActivity,
                        "Token tidak valid. Silakan login ulang.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@PengeluaranActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }


    // Fungsi untuk memperbarui status kategori yang dipilih
    private fun updateCategorySelection() {
        // Reset semua kategori menjadi warna default
        kategori1.setBackgroundColor(Color.WHITE)
        kategori2.setBackgroundColor(Color.WHITE)
        kategori3.setBackgroundColor(Color.WHITE)

        // Set kategori yang dipilih menjadi warna hijau
        when (selectedCategory) {
            kategori1Name.text.toString() -> kategori1.setBackgroundColor(Color.GREEN)
            kategori2Name.text.toString() -> kategori2.setBackgroundColor(Color.GREEN)
            kategori3Name.text.toString() -> kategori3.setBackgroundColor(Color.GREEN)
        }
    }
}



