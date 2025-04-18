package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class TableResponse(
    @SerializedName("tableExternalKey")
    val tableExternalKey: String? = null,
    @SerializedName("tableNumber")
    val tableNumber: Int? = null,
    @SerializedName("tableName")
    val tableName: String? = null,
    @SerializedName("orders")
    var orders: List<OrderResponse>? = null,
)
