package com.bvc.ordering.di

import com.bvc.data.datasource.YoRemoteDataSourceImpl
import com.bvc.data.repository.YoRepositoryImpl
import com.bvc.domain.repository.YoRepository
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
