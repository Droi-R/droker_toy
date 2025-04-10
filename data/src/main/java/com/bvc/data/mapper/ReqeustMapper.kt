package com.bvc.data.mapper

import com.bvc.data.remote.model.request.OptionsRequest
import com.bvc.data.remote.model.request.ProductItemsRequest
import com.bvc.data.remote.model.request.ProductOptionRequest
import com.bvc.data.remote.model.request.StockRequest
import com.bvc.domain.model.Options
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity

fun ProductEntity.toRequest(): ProductItemsRequest =
    ProductItemsRequest(
        externalKey = externalKey,
        name = name,
        categoryKey = categoryKey,
        categoryName = categoryName,
        descriptions = descriptions,
        isVat = isVat,
        selected = selected,
        stock =
            StockRequest(
                externalKey = stock.externalKey,
                useStock = stock.useStock,
                count = stock.count,
            ),
        color = color,
        image = image,
        price = price,
        productOption = productOption.map { it.toRequest() },
        position = position,
        quantity = quantity,
    )

fun ProductOptionEntity.toRequest(): ProductOptionRequest =
    ProductOptionRequest(
        id = id,
        name = name,
        required = required,
        minOptionCountLimit = minOptionCountLimit,
        maxOptionCountLimit = maxOptionCountLimit,
        position = position,
        options = ArrayList(options.map { it.toRequest() }),
    )

fun Options.toRequest(): OptionsRequest =
    OptionsRequest(
        id = id,
        name = name,
        price = price,
        position = position,
        useStock = useStock,
        stockQuantity = stockQuantity,
        isSoldOut = isSoldOut,
        isSelected = isSelected,
    )
