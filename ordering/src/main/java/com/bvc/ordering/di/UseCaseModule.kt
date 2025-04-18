package com.bvc.ordering.di

import com.bvc.domain.repository.MainRepository
import com.bvc.domain.repository.SplashRepository
import com.bvc.domain.usecase.MainUseCase
import com.bvc.domain.usecase.SplashUseCase
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
    fun provideSplashUseCase(repository: SplashRepository) = SplashUseCase(repository)

    @Provides
    @Singleton
    fun provideMainUseCase(repository: MainRepository) = MainUseCase(repository)
}
