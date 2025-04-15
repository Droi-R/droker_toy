package com.bvc.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialsResponse(
    @SerializedName("material_id")
    val materialId: String? = "",
    @SerializedName("material_name")
    val materialName: String? = "",
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("stock")
    val stock: Int? = 0,
    @SerializedName("safety_stock")
    val safetyStock: Int? = 0,
    @SerializedName("unit")
    val unit: String? = "",
) : Parcelable
