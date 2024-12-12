package com.example.anggarin.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class VoiceInputRequest(
    val fullTranscriptText: String
)

@Parcelize
data class VoiceInputResponse(
    val error: Boolean,
    val message: String,
    val incomeId: String
): Parcelable