package com.example.anggarin.data.remote2

import com.example.anggarin.data.response.InvestmentCalculationRequest
import com.example.anggarin.data.response.InvestmentCalculationResponse
import com.example.anggarin.data.response.InvestmentRecommendation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService2 {
    @POST("calculate-target-return")
    suspend fun calculateInvestment(
        @Body investmentCalculationRequest: InvestmentCalculationRequest
    ): Response<InvestmentCalculationResponse>

    @POST("recommendations")
    suspend fun getInvestmentRecommendations(
        @Body params: Map<String, Double>
    ): Response<List<InvestmentRecommendation>>
}