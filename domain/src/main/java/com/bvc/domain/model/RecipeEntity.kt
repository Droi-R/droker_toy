package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeEntity(
    val materialId: String,
    val name: String,
) : Parcelable {
    companion object {
        val EMPTY = RecipeEntity("", "")
    }
}
