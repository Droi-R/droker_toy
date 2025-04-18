package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class ApproveRequest(
    @SerializedName("payment_id") val paymentId: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("device_number") val deviceId: String,
    @SerializedName("qouta") val qouta: String = "00",
    @SerializedName("original_approved_id") val approvedId: String,
    @SerializedName("original_approved_date") val approvedDate: String,
    @SerializedName("refund_approved_id") val refundApprovedId: String? = null,
    @SerializedName("refund_approved_date") val refundApprovedDate: String? = null,
)
