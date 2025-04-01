package com.bvc.data.remote.model
import com.google.gson.annotations.SerializedName

data class ResMeta(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)

data class ResPagination(
    @SerializedName("next") val next: String? = "",
    @SerializedName("previous") val previous: String? = "",
    @SerializedName("count") val count: Int? = 0,
    @SerializedName("next_cursor") val nextCursor: String? = "",
)

data class ResData<T>(
    @SerializedName("data") val data: T,
    @SerializedName("meta") val meta: ResMeta,
)

data class ResDataList<T>(
    @SerializedName("data") val data: List<T>?,
    @SerializedName("meta") val meta: ResMeta,
    @SerializedName("pagination") val pagination: ResPagination? = ResPagination(),
)
