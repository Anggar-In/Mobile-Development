package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName

data class ExpenseRequest(

    @field:SerializedName("expense_amount")
    val expenseAmount: Int,

    @field:SerializedName("expense_date")
    val expenseDate: String,

    @field:SerializedName("store")
    val store: String,

    @field:SerializedName("items")
    val items: List<String>

)

data class ExpenseResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("expense_id")
    val expenseId: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("expense_amount")
    val expenseAmount: Int? = null,

    @field:SerializedName("expense_date")
    val expenseDate: String? = null,

    @field:SerializedName("store")
    val store: String? = null,

    @field:SerializedName("items")
    val items: List<String>? = null
)

data class Expense(
    @field:SerializedName("expense_id")
    val expenseId: String,

    @field:SerializedName("expense_amount")
    val expenseAmount: Int,

    @field:SerializedName("expense_date")
    val expenseDate: String,

    @field:SerializedName("store")
    val store: String,

    @field:SerializedName("items")
    val items: List<String>
)

data class MessageResponse(
    @field:SerializedName("message")
    val message: String
)

