package com.example.anggarin.ui.investasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.data.Result.Result
import com.example.anggarin.databinding.ActivityInputInvestasiBinding

class InputInvestasiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInputInvestasiBinding
    private val inputInvestasiViewModel: InputInvestasiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputInvestasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBtnBackInputinvestasi.setOnClickListener(this)
        binding.btnHitungInvestasi.setOnClickListener(this)

        // Observer untuk hasil perhitungan investasi
        inputInvestasiViewModel.investmentResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(this, "perhitungan berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HasilInvestasiActivity::class.java)
                    @Suppress("DEPRECATION")
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    intent.putExtra("roi", result.data.targetReturn)
                    startActivity(intent)
                }
                is Result.Error -> {
                    // Tampilkan pesan error
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.img_btn_back_inputinvestasi -> {
                startActivity(Intent(this, InvestasiActivity::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            R.id.btn_hitung_investasi -> {
                hitungInvestasi()
            }
        }
    }

    private fun hitungInvestasi() {
        // Ambil nilai dari input
        val annualExpenditure = binding.edtInputPengeluaranTahunan.text.toString().toLong()
        val initialInvestment = binding.edtInputInvestasiawal.text.toString().toLong()
        val timePeriod = binding.edtJangkainvestasi.text.toString().toInt()

        // Validasi input
        when {
            annualExpenditure <= 0 -> {
                binding.edtInputPengeluaranTahunan.error = "Masukkan pengeluaran tahunan yang valid"
                return
            }
            initialInvestment <= 0 -> {
                binding.edtInputInvestasiawal.error = "Masukkan investasi awal yang valid"
                return
            }
            timePeriod <= 0 -> {
                binding.edtJangkainvestasi.error = "Masukkan periode waktu yang valid"
                return
            }
            else -> {
                // Panggil fungsi hitung investasi di ViewModel
                inputInvestasiViewModel.calculateInvestment(
                    annualExpenditure = annualExpenditure,
                    initialInvestment = initialInvestment,
                    timePeriod = timePeriod
                )
            Log.e("InputInvestasiActivity", "Invalid input: $annualExpenditure, $initialInvestment, $timePeriod")
            }
        }
    }
}