package com.droi.data.datasource

import com.droi.data.api.RetrofitService
import com.droi.data.model.YoReponse
import javax.inject.Inject

class YoRemoteDataSourceImpl @Inject constructor(
    private val api: RetrofitService,
) : YoRemoteDataSource {
    override suspend fun getYo(q: String): YoReponse.ResData {
        return api.users(q)
    }
}
interface YoRemoteDataSource {
    suspend fun getYo(q: String): YoReponse.ResData
}
