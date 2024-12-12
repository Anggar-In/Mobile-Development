package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName


data class BudgetRequest(

    @field:SerializedName("income_month")
    val incomeMonth: Int,

    @field:SerializedName("budget_month")
    val budgetMonth: Int
)

data class BudgetResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("budget_id")
    val budgetId: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("income_month")
    val incomeMonth: Int? = null,

    @field:SerializedName("budget_month")
    val budgetMonth: Int? = null
)
