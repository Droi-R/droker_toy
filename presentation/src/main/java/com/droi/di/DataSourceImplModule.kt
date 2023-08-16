package com.droi.di

import com.droi.data.api.RetrofitService
import com.droi.data.datasource.YoRemoteDataSource
import com.droi.data.datasource.YoRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceImplModule {
    @Provides
    @Singleton
    fun provideMainDataSource(
        githubApi: RetrofitService,
    ): YoRemoteDataSource {
        return YoRemoteDataSourceImpl(
            githubApi,
        )
    }
}
