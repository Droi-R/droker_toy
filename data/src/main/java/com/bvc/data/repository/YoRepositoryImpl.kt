package com.bvc.data.repository

import com.bvc.data.datasource.YoRemoteDataSource
import com.bvc.data.mapper.Mapper
import com.bvc.domain.model.YoEntity
import com.bvc.domain.repository.YoRepository
import javax.inject.Inject

class YoRepositoryImpl @Inject constructor(
    private val dataSource: YoRemoteDataSource,
) : YoRepository {
    override suspend fun getUsers(q: String): YoEntity.Res {
        return Mapper.mapperToRes(dataSource.getYo(q))
    }
}
