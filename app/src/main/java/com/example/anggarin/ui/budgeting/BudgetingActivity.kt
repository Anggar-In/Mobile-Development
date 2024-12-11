package com.studio.anggarin.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.studio.anggarin.R

class BudgetingActivity : AppCompatActivity() {
    // Declare views for the form
    private lateinit var editTextPemasukan: EditText
    private lateinit var editTextBudget: EditText
    private lateinit var buttonSimpan: Button

    // Declare views for the result section
    private lateinit var textSisaBudget: TextView
    private lateinit var textAngkaSisaBudget: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var textTerpakai: TextView

    // Define variables to store the user input
    private var totalBudget: Int = 0
    private var pemasukan: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budgeting)

        // Initialize form views
        editTextPemasukan = findViewById(R.id.editTextPemasukan)
        editTextBudget = findViewById(R.id.editTextBudget)
        buttonSimpan = findViewById(R.id.button)

        // Initialize result views
        textSisaBudget = findViewById(R.id.textSisaBudget)
        textAngkaSisaBudget = findViewById(R.id.textAngkaSisaBudget)
        progressBar = findViewById(R.id.progressBar)
        textTerpakai = findViewById(R.id.textTerpakai)

        // Set the button click listener
        buttonSimpan.setOnClickListener {
            // Retrieve user input
            val pemasukanInput = editTextPemasukan.text.toString()
            val budgetInput = editTextBudget.text.toString()

            if (pemasukanInput.isNotEmpty() && budgetInput.isNotEmpty()) {
                pemasukan = pemasukanInput.toInt()
                totalBudget = budgetInput.toInt()

                // Calculate the remaining budget and the progress
                // diganti pengeluaran untuk pemasukannya
                val sisaBudget = totalBudget - pemasukan
                val progress = (pemasukan.toFloat() / totalBudget.toFloat() * 100).toInt()

                // Update the result section
                textAngkaSisaBudget.text = "Rp.$sisaBudget"
                progressBar.progress = progress
                textTerpakai.text = "Terpakai Rp.0/Rp.$totalBudget"

                // Change visibility dynamically
                // Hide the form layout and show the result layout
                findViewById<LinearLayout>(R.id.formLayout).visibility = View.GONE
                findViewById<LinearLayout>(R.id.resultLayout).visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}