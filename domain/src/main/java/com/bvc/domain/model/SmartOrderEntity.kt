package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SmartOrderEntity(
    val smartOrderId: String = "",
    // 발주 갯수
    val orderCount: Int = 0,
    val description: String = "",
    val deliveryCost: String = "",
    val logisticsCompany: String = "",
    val expectedConsumptionCount: Int = 0,
    val origin: OriginEntity,
    val material: MaterialsEntity,
) : Parcelable

@Parcelize
data class OriginEntity(
    val orignId: String,
    val name: String,
    val price: String,
    val deliveryCost: Int,
    val supplier: String,
) : Parcelable {
    companion object {
        val EMPTY = OriginEntity("", "", "", 0, "")
    }
}
