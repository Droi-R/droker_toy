package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialsEntity(
    val materialId: String,
    val materialName: String,
    val unitCount: Int,
    val unitSafetyCount: Int,
    val imageUrl: String,
    val unit: String,
) : Parcelable {
    companion object {
        val EMPTY = MaterialsEntity("", "", 0, 0, "", "")
    }
}
