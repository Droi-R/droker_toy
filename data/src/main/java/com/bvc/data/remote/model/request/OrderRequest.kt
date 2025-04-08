package com.bvc.data.remote.model.request

import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("product_items") val productItems: List<ProductItemsRequest>,
//    @SerializedName("order_name") var orderName: String? = "",
    @SerializedName("status") val status: OrderStatus,
    @SerializedName("order_from") val orderFrom: OrderFrom,
    @SerializedName("table_number") var tableNumber: String,
    @SerializedName("table_external_key") val tableExternalKey: String,
)
