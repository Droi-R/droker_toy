package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("payment_id") val paymentId: String? = null,
)
