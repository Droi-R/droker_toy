package com.bvc.ordering.di

import android.content.Context
import com.bvc.ordering.util.DefaultResourceProvider
import com.bvc.ordering.util.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context,
    ): ResourceProvider = DefaultResourceProvider(context)
}
