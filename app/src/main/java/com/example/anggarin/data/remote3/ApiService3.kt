package com.example.anggarin.data.remote3

import com.example.anggarin.data.response.InvestmentPrediction
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService3 {
    @GET("result/{nama}")
    suspend fun getInvestmentPrediction(
        @Path("nama") nama: String
    ): Response<List<InvestmentPrediction>>
}