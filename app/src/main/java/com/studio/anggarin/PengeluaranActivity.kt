package com.studio.anggarin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class PengeluaranActivity : AppCompatActivity() {

    private lateinit var editTextNominal: EditText
    private lateinit var buttonSelectDate: Button
    private lateinit var textViewDate: TextView
    private lateinit var buttonSaveTransaction: Button

    private var selectedDate: String? = null // Untuk menyimpan tanggal yang dipilih
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pengeluaran_activity)

        editTextNominal = findViewById(R.id.editTextNominal)
        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        textViewDate = findViewById(R.id.textViewDate)
        buttonSaveTransaction = findViewById(R.id.buttonSaveTransaction)

        sharedPreferences = getSharedPreferences("FinanceApp", Context.MODE_PRIVATE)

        buttonSelectDate.setOnClickListener {
            showDatePickerDialog()
        }

        buttonSaveTransaction.setOnClickListener {
            saveTransaction()
        }
    }

    @SuppressLint("NewApi")
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear" // Format DD/MM/YYYY
                textViewDate.text = "Tanggal: $selectedDate"
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun saveTransaction() {
        val nominal = editTextNominal.text.toString()

        if (nominal.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
            return
        }

        val totalPengeluaran = sharedPreferences.getFloat("TotalPengeluaran", 0f) + nominal.toFloat()
        with(sharedPreferences.edit()) {
            putFloat("TotalPengeluaran", totalPengeluaran)
            apply() // simpan
        }

        val formattedNominal = "Rp $nominal"
        Toast.makeText(this, "Transaksi disimpan: $formattedNominal pada tanggal: $selectedDate", Toast.LENGTH_SHORT).show()

        editTextNominal.text.clear()
        textViewDate.text = "Belum dipilih"
        selectedDate = null
    }
}