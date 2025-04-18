package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentEntity(
    val paymentId: String,
    var paymentAmout: String = "0",
    var vat: String = "",
    var supAmt: String = "",
    var taxFreeAmt: String = "",
//    val orderName: String,
//    var orderItems: List<ProductEntity>,
//    val buyerName: String,
//    val orderFrom: OrderFrom,
//    val paymentStatus: PaymentStatus,
//    val orderStatus: OrderStatus,
//    val tableNumber: Int,
//    val buyerPhoneNumber: String,
//    val additionalComment: String,
//    val orderNumber: Int,
//    val orderMemos: List<OrderMemo>,
) : Parcelable {
    companion object {
        val EMPTY =
            PaymentEntity(
                paymentId = "",
//                orderName = "",
//                orderItems = emptyList(),
//                buyerName = "",
//                orderFrom = OrderFrom.NONE,
//                paymentStatus = PaymentStatus.NONE,
//                orderStatus = OrderStatus.NONE,
//                tableNumber = 0,
//                buyerPhoneNumber = "",
//                additionalComment = "",
//                orderNumber = 0,
//                orderMemos = emptyList(),
            )
    }
}
