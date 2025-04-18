package com.bvc.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeResponse(
    @SerializedName("material_id")
    val materialId: String = "",
    @SerializedName("name")
    val name: String = "",
) : Parcelable
