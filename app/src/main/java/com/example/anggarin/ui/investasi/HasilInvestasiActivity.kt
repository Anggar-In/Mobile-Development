package com.example.anggarin.ui.investasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anggarin.R
import com.example.anggarin.data.Result.Result
import com.example.anggarin.databinding.ActivityHasilInvestasiBinding

class HasilInvestasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilInvestasiBinding
    private val inputInvestasiViewModel: InputInvestasiViewModel by viewModels()
    private val hasilInvestasiViewModel: HasilInvestasiViewModel by viewModels()
    private lateinit var investasiAdapter: InvestasiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilInvestasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBtnBackInputinvestasi.setOnClickListener {
            startActivity(Intent(this, InputInvestasiActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // Mengambil targetReturn dari Intent
        val targetReturn = intent.getDoubleExtra("roi", 0.0)

        investasiAdapter = InvestasiAdapter(mutableListOf(), targetReturn) { investment ->
            // Menyiapkan intent dengan data investasi
            val intent = Intent(this, DetailInvestasiActivity::class.java)
            intent.putExtra("investment", investment) // Kirim data sebagai Parcelable

            // Mendapatkan data prediction
            val predictions = hasilInvestasiViewModel.investmentPredictions.value
            Log.d("HasilInvestasiActivity", "Predictions to pass: $predictions")

            // Pastikan predictions tidak null
            if (predictions != null) {
                intent.putParcelableArrayListExtra("investmentPrediction", ArrayList(predictions))
            } else {
                Log.d("HasilInvestasiActivity", "Predictions are null")
            }

            // Mulai DetailInvestasiActivity setelah semua data siap
            startActivity(intent)
        }

        binding.rvInvestasi.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        // Observe perubahan data dari ViewModel
        hasilInvestasiViewModel.investmentRecommendations.observe(this) { result ->
            if (result.isNotEmpty()) {
                // Jika data tersedia, update adapter
                investasiAdapter.updateData(result, targetReturn)
                binding?.rvInvestasi?.adapter = investasiAdapter
            } else {
                // Tampilkan pesan error atau kosong
                Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }

        // Panggil ViewModel untuk mengambil rekomendasi investasi
        inputInvestasiViewModel.investmentRecommendations.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    Log.d("HasilInvestasiActivity", "Investment Recommendations: ${result.data}")
                    // Update data di ViewModel
                    hasilInvestasiViewModel.setInvestmentRecommendations(result.data)
                }
                is Result.Error -> {
                    // Tampilkan pesan error
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe perubahan data dari ViewModel untuk prediksi investasi
        hasilInvestasiViewModel.investmentPredictions.observe(this) { predictions ->
            // Pastikan Anda menerima prediksi dan menampilkan data jika ada
            if (predictions.isEmpty()) {
                Toast.makeText(this, "Tidak ada prediksi yang tersedia", Toast.LENGTH_SHORT).show()
            }
        }

        inputInvestasiViewModel.investmentPrediction.observe(this) { predictions ->
            // Safely handle potentially null or empty predictions
            val nonNullPredictions = predictions?.filterNotNull() ?: emptyList()
            hasilInvestasiViewModel.setInvestmentPredictions(nonNullPredictions)
        }

        // Panggil ViewModel untuk mendapatkan rekomendasi investasi
        inputInvestasiViewModel.investmentRecommendations.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    // Update data di ViewModel
                    hasilInvestasiViewModel.setInvestmentRecommendations(result.data)
                }
                is Result.Error -> {
                    // Tampilkan pesan error
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Panggil API untuk mendapatkan rekomendasi investasi dan prediksi
        inputInvestasiViewModel.getInvestmentRecommendations(targetReturn)
    }
}