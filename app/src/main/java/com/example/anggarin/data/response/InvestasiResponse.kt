package com.example.anggarin.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class InvestmentCalculationResponse(
    @field:SerializedName ("targetPortofolio") val targetPortfolio: String,
    @field:SerializedName ("roi") val roi: String,
    @field:SerializedName ("targetReturn") val targetReturn: Double
)

data class InvestmentCalculationRequest(
    @field:SerializedName ("annualExpenditure") val annualExpenditure: Long,
    @field:SerializedName ("initialInvestment") val initialInvestment: Long,
    @field:SerializedName ("timePeriod") val timePeriod: Int
)


// Investment Recommendation Response
@Parcelize
data class InvestmentRecommendation(
    @field:SerializedName ("stockCode") val stockCode: String,
    @field:SerializedName ("revenue") val revenue: String,
    @field:SerializedName ("netIncome") val netIncome: String,
    @field:SerializedName ("marketCap") val marketCap: String,
    @field:SerializedName ("annualEps") val annualEPS: Double,
    @field:SerializedName ("returnOnEquity") val returnOnEquity: Double,
    @field:SerializedName ("oneYearPriceReturns") val oneYearPriceReturns: Double,
    @field:SerializedName ("threeYearsPriceReturns") val threeYearPriceReturns: Double,
    @field:SerializedName ("fiveYearPriceReturns") val fiveYearPriceReturns: Double,
    @field:SerializedName ("dividendYield") val dividendYield: Double,
    @field:SerializedName ("payoutRatio")val payoutRatio: Double
): Parcelable

// Investment Prediction Data
@Parcelize
data class InvestmentPrediction(
    @field:SerializedName ("timestamp") val timestamp: String,
    @field:SerializedName ("low") val low: String,
    @field:SerializedName ("high") val high: String,
    @field:SerializedName ("close") val close: String
): Parcelable
