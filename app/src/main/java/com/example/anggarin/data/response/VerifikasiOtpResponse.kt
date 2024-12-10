package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName

data class VerifikasiOtpResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,
)

data class VerifikasiOtpRequest(
    @field:SerializedName("email") val email: String,
    @field:SerializedName("otp") val otp: String,
)



