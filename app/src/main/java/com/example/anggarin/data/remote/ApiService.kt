package com.example.anggarin.data.remote

import com.example.anggarin.data.response.LoginRequest
import com.example.anggarin.data.response.LoginResponse
import com.example.anggarin.data.response.RegisterRequest
import com.example.anggarin.data.response.RegisterResponse
import com.example.anggarin.data.response.ResendOtpRequest
import com.example.anggarin.data.response.ResendOtpResponse
import com.example.anggarin.data.response.VerifikasiOtpRequest
import com.example.anggarin.data.response.VerifikasiOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(
       @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>


    @POST("auth/verify-otp")
    suspend fun verificationUser(
        @Body verifikasiOtpRequest: VerifikasiOtpRequest
    ): Response<VerifikasiOtpResponse>


    @POST("resend-otp")
    suspend fun resendOtp(
        @Body resendOtpRequest: ResendOtpRequest
    ): Response<ResendOtpResponse>

    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}