package com.bvc.ordering.di

import com.bvc.domain.repository.GithubRepository
import com.bvc.domain.usecase.GetUserRepoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
//    @Provides
//    @Singleton
//    fun providePreferencesHelper(
//        @ApplicationContext context: Context,
//    ): PreferencesHelper = PreferencesHelper(context)

    @Provides
    @Singleton
    fun provideGetUserRepoUseCase(repository: GithubRepository) = GetUserRepoUseCase(repository)
}
