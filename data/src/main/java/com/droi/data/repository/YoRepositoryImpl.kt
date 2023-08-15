package com.droi.data.repository

import com.droi.data.datasource.YoRemoteDataSource
import com.droi.data.mapper.Mapper
import com.droi.domain.model.YoEntity
import com.droi.domain.repository.YoRepository

class YoRepositoryImpl(
    private val dataSource: YoRemoteDataSource,
) : YoRepository {
    override suspend fun getUsers(q: String): YoEntity.Res {
        return Mapper.mapperToRes(dataSource.getYo(q))
    }
}
