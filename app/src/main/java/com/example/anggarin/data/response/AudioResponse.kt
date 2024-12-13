package com.example.anggarin.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// Model untuk request
data class VoiceInputRequest(
    @SerializedName("fullTranscriptText")
    val fullTranscriptText: String
)

// Model untuk response
@Parcelize
data class VoiceInputResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("income_id")
    val incomeId: String
) : Parcelable
