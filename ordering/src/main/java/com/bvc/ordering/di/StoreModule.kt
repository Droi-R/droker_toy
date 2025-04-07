package com.bvc.ordering.di

import com.bvc.data.repository.CartStoreRepositoryImpl
import com.bvc.data.repository.OrderStoreRepositoryImpl
import com.bvc.domain.repository.CartStoreRepository
import com.bvc.domain.repository.OrderStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {
    @Binds
    @Singleton
    abstract fun bindCartStoreRepository(impl: CartStoreRepositoryImpl): CartStoreRepository

    @Binds
    @Singleton
    abstract fun bindOrderStoreRepository(impl: OrderStoreRepositoryImpl): OrderStoreRepository
}
