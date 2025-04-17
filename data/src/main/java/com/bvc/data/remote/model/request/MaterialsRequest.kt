package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class MaterialsRequest(
    @SerializedName("material_id") val materialId: String,
    @SerializedName("material_name") val materialName: String,
    @SerializedName("stock") val stock: Int,
    @SerializedName("safety_stock") val safetyStock: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("unit") val unit: String,
)
