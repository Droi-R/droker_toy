package com.bvc.data.remote.model.request

import com.bvc.domain.type.PaymentChannel
import com.bvc.domain.type.PaymentMethod
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.PaymentType
import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("store_id") val storeId: String,
    @SerializedName("order_product_ids") val orderProductIds: List<String>,
    @SerializedName("total_price") val totalPrice: String,
    @SerializedName("payment_method") val paymentMethod: PaymentMethod,
    @SerializedName("payment_channel") val paymentChannel: PaymentChannel,
    @SerializedName("payment_Status") val paymentStatus: PaymentStatus,
    @SerializedName("payment_type") val paymentType: PaymentType,
)
