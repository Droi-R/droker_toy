package com.bvc.ordering.di

import com.bvc.data.remote.api.GithubApi
import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.api.SplashApi
import com.bvc.data.repository.remote.datasource.GithubDataSource
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.data.repository.remote.datasourceImpl.GithubDataSourceImpl
import com.bvc.data.repository.remote.datasourceImpl.MainDataSourceImpl
import com.bvc.data.repository.remote.datasourceImpl.SplashDataSourceImpl
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
    fun provideGithubDataSource(githubApi: GithubApi): GithubDataSource =
        GithubDataSourceImpl(
            githubApi,
        )

    @Provides
    @Singleton
    fun provideSplashDataSource(splahApi: SplashApi): SplashDataSource =
        SplashDataSourceImpl(
            splahApi,
        )

    @Provides
    @Singleton
    fun provideMainDataSource(mainApi: MainApi): MainDataSource =
        MainDataSourceImpl(
            mainApi,
        )
}
