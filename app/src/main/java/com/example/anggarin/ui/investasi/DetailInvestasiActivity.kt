package com.example.anggarin.ui.investasi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.anggarin.R
import com.example.anggarin.data.response.InvestmentPrediction
import com.example.anggarin.data.response.InvestmentRecommendation
import com.example.anggarin.databinding.ActivityDetailInvestasiBinding

class DetailInvestasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailInvestasiBinding
    private val inputInvestasiViewModel: InputInvestasiViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInvestasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val investment = intent.getParcelableExtra<InvestmentRecommendation>("investment")

        @Suppress("DEPRECATION")
        val predictions = intent.getParcelableArrayListExtra<InvestmentPrediction>("investmentPrediction")

        // Log detailed information
        Log.d("DetailInvestasiActivity", "Investment: $investment")
        Log.d("DetailInvestasiActivity", "Predictions received: $predictions")
        Log.d("DetailInvestasiActivity", "Predictions size: ${predictions?.size}")


        investment?.let {
            binding.txtNamaSahamaDetail.text = it.stockCode
            binding.txtMarketCapDetail.text = "Market Cap: (${it.marketCap})"
            binding.txtRevenue.text = "Revenue: (${it.revenue})"
            binding.txtNetincome.text = "Net Income: (${it.netIncome})"
            binding.txtAnnualEPS.text = "Annual EPS: (${it.annualEPS})"
            binding.txtReturnOnEquity.text = "Return On Equity: (${it.returnOnEquity})"
            binding.txt1yearPriceReturn.text = "1 Years Price Return: (${it.oneYearPriceReturns})"
            binding.txt3yearPriceReturn.text = "3 Years Price Return: (${it.threeYearPriceReturns})"
            binding.txt5yearPriceReturn.text = "5 Years Price Return: (${it.fiveYearPriceReturns})"
            binding.txtDividendYield.text = "Dividend Yield: (${it.dividendYield})"
            binding.txtPayoutRatio.text = "Payout Ratio: (${it.payoutRatio})"
        }

        inputInvestasiViewModel.investmentPrediction.observe(this) { predictionList ->
            Log.d("DetailInvestasiActivity", "Received prediction list: $predictionList")
            predictionList?.let {
                // Cek jika ada data dalam list
                if (it.isNotEmpty()) {
                    val prediction = it[0]  // Mengambil prediksi pertama dari list
                    binding.txtTimestamp.text = prediction?.timestamp
                    binding.txtLowPrice.text = prediction?.low
                    binding.txtHighPrice.text = prediction?.high
                    binding.txtClosePrice.text = prediction?.close
                } else {
                    // Tangani jika list kosong
                    binding.txtTimestamp.text = "No prediction data available"
                    binding.txtLowPrice.text = "No prediction data"
                    binding.txtHighPrice.text = "No prediction data"
                    binding.txtClosePrice.text = "No prediction data"
                }
            }
        }

        binding.imgBtnBackDetailinvestasi.setOnClickListener {
            startActivity(Intent(this, InputInvestasiActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}