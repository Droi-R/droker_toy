package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class MaterialsRequest(
    @SerializedName("material_id") val materialId: String,
    @SerializedName("material_name") val materialName: String,
    @SerializedName("unit_count") val unitCount: Int,
    @SerializedName("unit_safety_count") val unitSafetyCount: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("unit") val unit: String,
)
