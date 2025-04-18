package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class CaptureRequest(
    @SerializedName("payment_id") val paymentId: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("approved_id") val approvedId: String,
    @SerializedName("approved_date") val approvedDate: String,
)
