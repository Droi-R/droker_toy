package com.bvc.data.remote.model.response

import com.bvc.domain.type.OrderFrom
import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("oid")
    val oid: String = "",
    @SerializedName("order_name")
    val orderName: String = "",
    @SerializedName("order_items")
    var orderItems: ArrayList<ProductResponse> = arrayListOf(),
    @SerializedName("buyer_name")
    val buyerName: String = "",
    @SerializedName("order_from")
    val orderFrom: OrderFrom = OrderFrom.POS,
    @SerializedName("table_number")
    val tableNumber: Int = 0,
    @SerializedName("buyer_phone_number")
    val buyerPhoneNumber: String = "",
    @SerializedName("additional_comment")
    val additionalComment: String = "",
    @SerializedName("order_number")
    val orderNumber: Int = 0,
    @SerializedName("order_memos")
    val orderMemos: ArrayList<OrderMemoResponse> = arrayListOf(),
)

data class OrderMemoResponse(
    @SerializedName("external_key")
    val externalKey: String = "",
    @SerializedName("oid")
    val oid: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
)
