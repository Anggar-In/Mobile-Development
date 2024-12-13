package com.example.anggarin.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("user")
    val user: User

)

data class User(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("email") val email: String
)

data class LoginRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,
)
