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
//        image = image,
        productOption = optionGroups.map { it.toRequest() },
        position = position,
        quantity = quantity,
        imageUrl = imageUrl, // 추가
        basePrice = basePrice, // 추가
        isSoldOut = isSoldOut, // 추가
        isVatIncluded = isVatIncluded, // 추가
        barcode = barcode, // 추가
        dailyLimit = dailyLimit, // 추가
    )

fun ProductOptionEntity.toRequest(): ProductOptionRequest =
    ProductOptionRequest(
        id = optionGroupId,
        name = name,
        required = required,
        minOptionCountLimit = minOptionCountLimit,
        maxOptionCountLimit = maxOptionCountLimit,
        position = position,
        options = ArrayList(options.map { it.toRequest() }),
    )

fun Options.toRequest(): OptionsRequest =
    OptionsRequest(
        id = productOptionsId,
        name = name,
        price = price,
        position = position,
        useStock = useStock,
        isSoldOut = isSoldOut,
        isSelected = isSelected,
    )
