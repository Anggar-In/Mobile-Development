package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName


data class Category(
    @field:SerializedName("category_id")
    val categoryId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("transaction_type")
    val transactionType: String
)

data class CreateCategoryResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("category_id")
    val categoryId: String
)

data class CategoryRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("transaction_type")
    val transactionType: String
)