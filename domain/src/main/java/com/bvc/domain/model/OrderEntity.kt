package com.bvc.domain.model

import com.bvc.domain.type.OrderFrom

data class OrderEntity(
    val oid: String = "",
    val orderName: String = "",
    var orderItems: ArrayList<ProductEntity> = arrayListOf(),
    val buyerName: String = "",
    val orderFrom: OrderFrom = OrderFrom.POS,
    val tableNumber: Int = 0,
    val buyerPhoneNumber: String = "",
    val additionalComment: String = "",
    val orderNumber: Int = 0,
    val orderMemos: ArrayList<OrderMemo> = arrayListOf(),
)

data class OrderMemo(
    val externalKey: String = "",
    val oid: String = "",
    val content: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)
