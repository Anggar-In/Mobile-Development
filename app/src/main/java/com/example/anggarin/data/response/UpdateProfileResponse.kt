package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @field:SerializedName("fullname")
    val fullName: String? = null,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    )

data class UpdateProfileRequest(
    @field:SerializedName("fullname")
    val fullName: String,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String
)
