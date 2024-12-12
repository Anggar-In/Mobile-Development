package com.example.anggarin.ui.transaksi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R

class PemasukanActivity : AppCompatActivity() {

    private lateinit var inputPemasukan: EditText
    private lateinit var btnSavePemasukan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pemasukan)// Layout untuk PemasukanActivity

        

        btnSavePemasukan.setOnClickListener {
            val pemasukan = inputPemasukan.text.toString()

            if (pemasukan.isEmpty()) {
                Toast.makeText(this, "Masukkan nilai pemasukan", Toast.LENGTH_SHORT).show()
            } else {
                // Lakukan penyimpanan data atau proses lebih lanjut
                Toast.makeText(this, "Pemasukan disimpan: $pemasukan", Toast.LENGTH_LONG).show()
            }
        }
    }
}