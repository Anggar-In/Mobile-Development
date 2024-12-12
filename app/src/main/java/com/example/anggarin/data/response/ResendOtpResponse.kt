package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName

data class ResendOtpResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,
)

data class ResendOtpRequest(
    @field:SerializedName("email") val email: String
)

