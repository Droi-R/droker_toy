package com.bvc.ordering.di

import com.bvc.data.api.RetrofitService
import com.bvc.data.datasource.YoRemoteDataSource
import com.bvc.data.datasource.YoRemoteDataSourceImpl
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
