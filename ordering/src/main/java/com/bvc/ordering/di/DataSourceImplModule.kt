package com.bvc.ordering.di

import com.bvc.data.remote.api.GithubApi
import com.bvc.data.repository.remote.datasource.GithubDataSource
import com.bvc.data.repository.remote.datasourceImpl.GithubDataSourceImpl
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
    fun provideMainDataSource(githubApi: GithubApi): GithubDataSource =
        GithubDataSourceImpl(
            githubApi,
        )
}
