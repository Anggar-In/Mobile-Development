package com.example.anggarin.ui.investasi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anggarin.data.Result.Result
import com.example.anggarin.data.remote2.ApiConfig2
import com.example.anggarin.data.remote3.ApiConfig3
import com.example.anggarin.data.response.InvestmentCalculationRequest
import com.example.anggarin.data.response.InvestmentCalculationResponse
import com.example.anggarin.data.response.InvestmentPrediction
import com.example.anggarin.data.response.InvestmentRecommendation
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class InputInvestasiViewModel : ViewModel() {

    private val _investmentPrediction = MutableLiveData<List<InvestmentPrediction?>?>()
    val investmentPrediction: LiveData<List<InvestmentPrediction?>?> = _investmentPrediction

    private val _targetReturn = MutableLiveData<Double>()
    val targetReturn: LiveData<Double> = _targetReturn

    private val _investmentResult = MutableLiveData<Result<InvestmentCalculationResponse>>()
    val investmentResult: LiveData<Result<InvestmentCalculationResponse>> = _investmentResult

    private val _investmentRecommendations =
        MutableLiveData<Result<List<InvestmentRecommendation>>>()
    val investmentRecommendations: LiveData<Result<List<InvestmentRecommendation>>> =
        _investmentRecommendations

    fun calculateInvestment(annualExpenditure: Long, initialInvestment: Long, timePeriod: Int) {
        viewModelScope.launch {
            try {
                // Membuat request object
                val request = InvestmentCalculationRequest(
                    annualExpenditure = annualExpenditure,
                    initialInvestment = initialInvestment,
                    timePeriod = timePeriod
                )
                Log.d(TAG, "Request Data: $request")

                // Memanggil API
                val response = ApiConfig2.getApiService2().calculateInvestment(request)

                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Log.i(TAG, "isSuccessfull ${response.isSuccessful}")
                        _targetReturn.postValue(data.targetReturn) // Simpan targetReturn
                        val inputInvestasiResponse = response.body()
                        _investmentResult.postValue(Result.Success(inputInvestasiResponse!!))
                    } ?: run {
                        _investmentResult.postValue(Result.Error("Respons kosong dari server"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage =
                        "Gagal menghitung investasi. Status: ${response.code()}, Pesan: $errorBody"
                    _investmentResult.postValue(Result.Error(errorMessage))
                }
            } catch (e: Exception) {
                _investmentResult.postValue(
                    Result.Error(e.localizedMessage ?: "Terjadi kesalahan tidak dikenal")
                )
            }
        }
    }

    fun getInvestmentRecommendations(targetReturn: Double) {
        viewModelScope.launch {
            try {
                val roundedTargetReturn =
                    BigDecimal(targetReturn).setScale(2, RoundingMode.HALF_UP).toDouble()

                val params = mapOf("targetReturn" to roundedTargetReturn)

                // Memanggil API
                val response = ApiConfig2.getApiService2().getInvestmentRecommendations(params)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.i(TAG, "isSuccessfull ${response.isSuccessful}")
                        _investmentRecommendations.postValue(Result.Success(it))

                        val stockcode =
                            it.firstOrNull()?.stockCode // Pastikan stockCode ada di response

                        // Jika ada stockcode, lanjutkan untuk memanggil getInvestmentPrediction
                        stockcode?.let { code ->
                            getInvestmentPrediction(code)
                        }
                    } ?: run {
                        _investmentRecommendations.postValue(Result.Error("Respons kosong dari server"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage =
                        "Gagal mendapatkan rekomendasi investasi. Status: ${response.code()}, Pesan: $errorBody"
                    _investmentRecommendations.postValue(Result.Error(errorMessage))
                }
            } catch (e: Exception) {
                _investmentRecommendations.postValue(
                    Result.Error(e.localizedMessage ?: "Terjadi kesalahan tidak dikenal")
                )
            }
        }
    }

    // Fungsi untuk memanggil getInvestmentPrediction berdasarkan stockcode
    private fun getInvestmentPrediction(stockCode: String) {
        viewModelScope.launch {
            try {
                // Memanggil API untuk mendapatkan prediksi investasi berdasarkan stockcode
                val response = ApiConfig3.getApiService3().getInvestmentPrediction(stockCode)

                if (response.isSuccessful) {
                    // Mendapatkan body response yang berisi List<InvestmentPrediction>
                    response.body()?.let { predictionList ->
                        Log.i(TAG, "Prediksi investasi untuk $stockCode: $predictionList")
                        // Menyimpan seluruh list prediksi ke LiveData
                        _investmentPrediction.postValue(predictionList)
                    } ?: run {
                        _investmentPrediction.postValue(null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage =
                        "Gagal mendapatkan prediksi investasi. Status: ${response.code()}, Pesan: $errorBody"
                    Log.e(TAG, errorMessage)
                    _investmentPrediction.postValue(null)
                }
            } catch (e: Exception) {
                _investmentPrediction.postValue(null)
                Log.e(TAG, "Terjadi kesalahan: ${e.localizedMessage}")
            }
        }
    }

    fun setInvestmentPredictions(predictions: List<InvestmentPrediction>) {
        _investmentPrediction.value = predictions
    }

    companion object {
        private const val TAG = "InputInvestasiViewModel"
    }
}
