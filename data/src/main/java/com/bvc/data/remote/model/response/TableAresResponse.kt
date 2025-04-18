package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class TableAresResponse(
    @SerializedName("table_area_id")
    val tableAreaId: String? = null,
    @SerializedName("store_id")
    val storeId: String? = null,
    @SerializedName("area_name")
    val areaName: String? = null,
    @SerializedName("selected")
    val selected: Boolean? = null,
)
