package com.example.anggarin.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ReceiptResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("expense_id")
    val expenseId: String? = null,
    @SerializedName("extracted_text")
    val extractedText: String? = null,
    @SerializedName("parsed_receipt")
    val parsedReceipt: ParsedReceipt? = null
)

@Parcelize
data class ParsedReceipt(
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("total")
    val total: String? = null,
    @SerializedName("items")
    val items: List<String>? = null
):Parcelable

