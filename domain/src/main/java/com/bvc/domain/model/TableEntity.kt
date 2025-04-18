package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TableEntity(
    val tableExternalKey: String = "",
    val tableNumber: Int = 0,
    val tableName: String = "",
    var orders: List<OrderEntity> = listOf(),
) : Parcelable
