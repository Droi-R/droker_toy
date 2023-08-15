package com.droi.data.model

import com.droi.domain.model.YoEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class YoReponse {
    data class ResData(
        @SerializedName("total_count") val total_count: Int,
        @SerializedName("incomplete_results") val incomplete_results: Boolean,
        @SerializedName("items") val items: ArrayList<YoEntity.Items>,
    ) : Serializable
}


