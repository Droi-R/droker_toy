package com.droi.di

import com.droi.data.datasource.YoRemoteDataSourceImpl
import com.droi.data.repository.YoRepositoryImpl
import com.droi.domain.repository.YoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        yoRemoteDataSourceImpl: YoRemoteDataSourceImpl,
    ): YoRepository {
        return YoRepositoryImpl(
            yoRemoteDataSourceImpl,
        )
    }
}
