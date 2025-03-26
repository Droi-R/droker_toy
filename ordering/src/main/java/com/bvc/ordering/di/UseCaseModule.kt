package com.bvc.ordering.di

import com.bvc.domain.repository.YoRepository
import com.bvc.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserRepoUseCase(repository: YoRepository) = GetUserUseCase(repository)
}