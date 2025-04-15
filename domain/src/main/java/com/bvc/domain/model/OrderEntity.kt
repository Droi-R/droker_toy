package com.bvc.domain.model

import android.os.Parcelable
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderEntity(
    val oid: String,
    val orderName: String,
    var orderItems: List<ProductEntity>,
    val buyerName: String,
    val orderFrom: OrderFrom,
    val paymentStatus: PaymentStatus,
    val orderStatus: OrderStatus,
    val tableNumber: Int,
    val buyerPhoneNumber: String,
    val additionalComment: String,
    val orderNumber: Int,
    val orderMemos: List<OrderMemo>,
) : Parcelable {
    companion object {
        val EMPTY =
            OrderEntity(
                oid = "",
                orderName = "",
                orderItems = emptyList(),
                buyerName = "",
                orderFrom = OrderFrom.NONE,
                paymentStatus = PaymentStatus.NONE,
                orderStatus = OrderStatus.NONE,
                tableNumber = 0,
                buyerPhoneNumber = "",
                additionalComment = "",
                orderNumber = 0,
                orderMemos = emptyList(),
            )
    }
}

@Parcelize
data class OrderMemo(
    val externalKey: String,
    val oid: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
) : Parcelable
