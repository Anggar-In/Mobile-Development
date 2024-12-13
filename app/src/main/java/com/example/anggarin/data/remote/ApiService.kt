package com.example.anggarin.data.remote


import com.example.anggarin.data.response.BudgetRequest
import com.example.anggarin.data.response.BudgetResponse
import com.example.anggarin.data.response.Category
import com.example.anggarin.data.response.CategoryRequest
import com.example.anggarin.data.response.CreateCategoryResponse
import com.example.anggarin.data.response.Expense
import com.example.anggarin.data.response.ExpenseRequest
import com.example.anggarin.data.response.ExpenseResponse
import com.example.anggarin.data.response.LoginRequest
import com.example.anggarin.data.response.LoginResponse
import com.example.anggarin.data.response.LogoutResponse
import com.example.anggarin.data.response.MessageResponse
import com.example.anggarin.data.response.ReceiptResponse

import com.example.anggarin.data.response.RegisterRequest
import com.example.anggarin.data.response.RegisterResponse
import com.example.anggarin.data.response.ResendOtpRequest
import com.example.anggarin.data.response.ResendOtpResponse
import com.example.anggarin.data.response.UpdateProfileRequest
import com.example.anggarin.data.response.UpdateProfileResponse
import com.example.anggarin.data.response.VerifikasiOtpRequest
import com.example.anggarin.data.response.VerifikasiOtpResponse

import com.example.anggarin.data.response.VoiceInputRequest
import com.example.anggarin.data.response.VoiceInputResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


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

    @POST("auth/logout")
    suspend fun logoutUser(
        @Header("Authorization") token: String
    ): Response<LogoutResponse>

    @PUT("user/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @POST("/budget")
    suspend fun postBudget(
        @Header("Authorization") token: String,
        @Body budgetRequest: BudgetRequest
    ): Response<BudgetResponse>

    // GET Budget
    @GET("/budget/{user_id}")
    suspend fun getBudget(
        @Header("Authorization") token: String,
        @Path("user_id") userId: String
    ): Response<List<BudgetResponse>>

    // PUT Budget
    @PUT("/budget/{budget_id}")
    suspend fun updateBudget(
        @Header("Authorization") token: String,
        @Path("budget_id") budgetId: String,
        @Body budgetRequest: BudgetRequest
    ): Response<BudgetResponse>

    // DELETE Budget
    @DELETE("/budget/{budget_id}")
    suspend fun deleteBudget(
        @Header("Authorization") token: String,
        @Path("budget_id") budgetId: String
    ): Response<BudgetResponse>

    @POST("/expense")
    suspend fun postExpense(
        @Header("Authorization") token: String,
        @Header("category_id") categoryId: String,
        @Body expense: ExpenseRequest
    ): Response<ExpenseResponse>

    @GET("/expense/{user_id}")
    suspend fun getExpenses(
        @Header("Authorization") token: String,
        @Path("user_id") userId: String
    ): Response<List<Expense>>

    @PUT("/expense/{expense_id}")
    suspend fun updateExpense(
        @Header("Authorization") token: String,
        @Path("expense_id") expenseId: String,
        @Body expense: ExpenseRequest
    ): Response<MessageResponse>

    @DELETE("/expense/{expense_id}")
    suspend fun deleteExpense(
        @Header("Authorization") token: String,
        @Path("expense_id") expenseId: String
    ): Response<MessageResponse>

    @GET("/category")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): Response<List<Category>>

    @POST("/category")
    suspend fun postCategory(
        @Header("Authorization") token: String,
        @Body categoryRequest: CategoryRequest
    ): Response<CreateCategoryResponse>

    @PUT("/category/{category_id}")
    suspend fun updateCategory(
        @Header("Authorization") token: String,
        @Path("category_id") categoryId: String,
        @Body categoryRequest: CategoryRequest
    ): Response<MessageResponse>

    @DELETE("/category/{category_id}")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Path("category_id") categoryId: String
    ): Response<MessageResponse>

    @Multipart
    @POST("/upload-receipt")
    suspend fun uploadReceipt(
        @Header("Authorization") authHeader: String,
        @Part receipt: MultipartBody.Part
    ): Response<ReceiptResponse>

    @POST("/voice-input")
    fun submitVoiceInput(
        @Header("Authorization") token: String,
        @Header("category_id") categoryId: String,
        @Body request: VoiceInputRequest
    ): Response<VoiceInputResponse>

}


