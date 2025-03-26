package com.bvc.domain.repository

import com.bvc.domain.model.YoEntity

interface YoRepository {
    suspend fun getUsers(q: String): YoEntity.Res
}
