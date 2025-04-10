package com.bvc.domain.model

import android.os.Parcelable
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderEntity(
    val oid: String = "",
    val orderName: String = "",
    var orderItems: ArrayList<ProductEntity> = arrayListOf(),
    val buyerName: String = "",
    val orderFrom: OrderFrom = OrderFrom.POS,
    val orderStatus: OrderStatus = OrderStatus.READY,
    val tableNumber: Int = 0,
    val buyerPhoneNumber: String = "",
    val additionalComment: String = "",
    val orderNumber: Int = 0,
    val orderMemos: ArrayList<OrderMemo> = arrayListOf(),
) : Parcelable

@Parcelize
data class OrderMemo(
    val externalKey: String = "",
    val oid: String = "",
    val content: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
) : Parcelable
