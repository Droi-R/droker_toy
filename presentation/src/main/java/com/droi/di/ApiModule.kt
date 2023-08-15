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
object ApiModule {
    @Provides
    @Singleton
    fun provideMainDataSource(
        retrofitService: RetrofitService,
    ): YoRemoteDataSource {
        return YoRemoteDataSourceImpl(
            retrofitService,
        )
    }
}
