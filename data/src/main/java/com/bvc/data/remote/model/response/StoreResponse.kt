package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("store_id")
    val storeId: Int? = null,
    @SerializedName("owner_id")
    val ownerID: Int? = null,
    @SerializedName("cats")
    val cats: List<CatsResponse>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("is_active")
    val isActive: Boolean? = null,
    @SerializedName("type")
    val type: String? = null,
)

data class CatsResponse(
    @SerializedName("cat_id")
    val catId: String? = null,
)
