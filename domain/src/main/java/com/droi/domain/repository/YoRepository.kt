package com.droi.domain.repository

import com.droi.domain.model.YoEntity

interface YoRepository {
    suspend fun getUsers(q: String): YoEntity.Res
}
