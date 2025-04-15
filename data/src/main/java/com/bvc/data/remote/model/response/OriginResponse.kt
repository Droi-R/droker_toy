package com.bvc.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OriginResponse(
    @SerializedName("origin_id")
    val originId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("delivery_cost")
    val deliveryCost: Int = 0,
    @SerializedName("supplier")
    val supplier: String = "",
) : Parcelable
