package com.bvc.data.remote.model.response

import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("oid")
    val oid: String? = null,
    @SerializedName("order_name")
    val orderName: String? = null,
    @SerializedName("order_items")
    var orderItems: ArrayList<ProductResponse>? = null,
    @SerializedName("buyer_name")
    val buyerName: String? = null,
    @SerializedName("order_from")
    val orderFrom: OrderFrom? = null,
    @SerializedName("order_status")
    val orderStatus: OrderStatus? = null,
    @SerializedName("table_number")
    val tableNumber: Int? = null,
    @SerializedName("buyer_phone_number")
    val buyerPhoneNumber: String? = null,
    @SerializedName("additional_comment")
    val additionalComment: String? = null,
    @SerializedName("order_number")
    val orderNumber: Int? = null,
    @SerializedName("order_memos")
    val orderMemos: ArrayList<OrderMemoResponse>? = null,
)

data class OrderMemoResponse(
    @SerializedName("external_key")
    val externalKey: String? = null,
    @SerializedName("oid")
    val oid: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
)
