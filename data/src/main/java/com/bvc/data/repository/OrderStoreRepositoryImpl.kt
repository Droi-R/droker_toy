package com.bvc.data.repository

import com.bvc.domain.model.OrderEntity
import com.bvc.domain.repository.OrderStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderStoreRepositoryImpl
    @Inject
    constructor() : OrderStoreRepository {
        private var currentOrder: OrderEntity? = null

        override fun setOrder(item: OrderEntity) {
            currentOrder = item
        }

        override fun getOrder(): OrderEntity? = currentOrder

        override fun clearOrder() {
            currentOrder = null
        }
    }
