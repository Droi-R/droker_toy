package com.bvc.data.remote.model.request

import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("product_items") val productItems: List<ProductItemsRequest>,
    @SerializedName("payment_status") val paymentStatus: PaymentStatus,
    @SerializedName("order_status") val orderStatus: OrderStatus,
    @SerializedName("order_from") val orderFrom: OrderFrom,
    @SerializedName("table_number") var tableNumber: String,
    @SerializedName("table_external_key") val tableExternalKey: String,
)
