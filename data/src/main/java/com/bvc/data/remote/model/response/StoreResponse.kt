package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("store_id")
    val storeId: Int? = null,
    @SerializedName("owner_id")
    val ownerID: Int? = null,
    @SerializedName("tid")
    val tid: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("is_active")
    val isActive: Int? = null,
    @SerializedName("type")
    val type: String? = null,
)
