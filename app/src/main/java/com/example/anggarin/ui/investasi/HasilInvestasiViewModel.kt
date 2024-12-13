package com.example.anggarin.ui.investasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anggarin.data.response.InvestmentPrediction
import com.example.anggarin.data.response.InvestmentRecommendation

class HasilInvestasiViewModel : ViewModel() {

    // LiveData untuk menyimpan rekomendasi investasi
    private val _investmentRecommendations = MutableLiveData<List<InvestmentRecommendation>>()
    val investmentRecommendations: LiveData<List<InvestmentRecommendation>> = _investmentRecommendations

    // LiveData untuk menyimpan prediksi investasi
    private val _investmentPredictions = MutableLiveData<List<InvestmentPrediction>>()
    val investmentPredictions: LiveData<List<InvestmentPrediction>> = _investmentPredictions

    // Method untuk mengupdate data rekomendasi investasi
    fun setInvestmentRecommendations(recommendations: List<InvestmentRecommendation>) {
        _investmentRecommendations.value = recommendations
    }

    // Method untuk mengupdate data prediksi investasi
    fun setInvestmentPredictions(predictions: List<InvestmentPrediction>) {
        _investmentPredictions.value = predictions
    }
}

