package com.bvc.data.remote.model.response
import com.google.gson.annotations.SerializedName

data class ResMeta(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null,
)

data class ResPagination(
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next_cursor") val nextCursor: String? = null,
)

data class ResData<T>(
    @SerializedName("data") val data: T? = null,
    @SerializedName("meta") val meta: ResMeta? = null,
)

data class ResDataList<T>(
    @SerializedName("data") val data: List<T>? = null,
    @SerializedName("meta") val meta: ResMeta? = null,
    @SerializedName("pagination") val pagination: ResPagination? = null,
)
