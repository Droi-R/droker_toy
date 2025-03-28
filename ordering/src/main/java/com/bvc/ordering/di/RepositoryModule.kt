package com.bvc.ordering.di

import com.bvc.data.repository.GithubRepositoryImpl
import com.bvc.data.repository.remote.datasourceImpl.GithubDataSourceImpl
import com.bvc.domain.repository.GithubRepository
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
    fun provideMainRepository(githubDataSourceImpl: GithubDataSourceImpl): GithubRepository =
        GithubRepositoryImpl(
            githubDataSourceImpl,
        )
}
