package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName

data class TabunganResponse (

    @field:SerializedName("message")
    val message: String? = null,

)

data class TabunganRequest(
    @field:SerializedName("goal_name")
    val goalName: String,

    @field:SerializedName("target_amount")
    val targetAmount: Int,

    @field:SerializedName("current_amount")
    val currentAmount: Int,

    @field:SerializedName("start_date")
    val startDate: String,

    @field:SerializedName("goal_deadline")
    val goalDeadline: String,
)