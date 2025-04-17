package com.bvc.data.mapper

import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.GithubResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.MaterialsResponse
import com.bvc.data.remote.model.response.OptionGroupsResponse
import com.bvc.data.remote.model.response.OptionResponse
import com.bvc.data.remote.model.response.OrderMemoResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.OriginResponse
import com.bvc.data.remote.model.response.PaymentResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.ResMeta
import com.bvc.data.remote.model.response.ResPagination
import com.bvc.data.remote.model.response.SmartOrderResponse
import com.bvc.data.remote.model.response.StockResponse
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.remote.model.response.TableResponse
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.Cats
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.Meta
import com.bvc.domain.model.OptionsEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.OrderMemo
import com.bvc.domain.model.OriginEntity
import com.bvc.domain.model.Pagination
import com.bvc.domain.model.PaymentEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.domain.model.Stock
import com.bvc.domain.model.Store
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.model.UserEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus

object ResponseMapper {
    fun mapGithub(response: List<GithubResponse>?): List<GithubEntity> =
        response?.map {
            GithubEntity(
                id = it.id,
                name = it.name,
                date = it.date,
                url = it.url,
            )
        } ?: emptyList()

    private fun mapMeta(meta: ResMeta?): Meta =
        Meta(
            code = meta?.code ?: 0,
            message = meta?.message ?: "",
        )

    private fun mapPagination(pagination: ResPagination?): Pagination =
        Pagination(
            next = pagination?.next ?: "",
            previous = pagination?.previous ?: "",
            count = pagination?.count ?: 0,
            nextCursor = pagination?.nextCursor ?: "",
        )

    fun mapSendSms(response: ResData<EmptyResponse>?): ApiData<EmptyEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data =
                EmptyEntity(
                    nothing = response?.data?.nothing,
                ),
        )

    fun mapVerifySms(response: ResData<EmptyResponse>?): ApiData<EmptyEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data =
                EmptyEntity(
                    nothing = response?.data?.nothing,
                ),
        )

    fun mapSignUp(response: ResData<LoginResponse>?): ApiData<LoginEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data =
                LoginEntity(
                    accessToken = response?.data?.accessToken ?: "",
                    refreshToken = response?.data?.refreshToken ?: "",
                    mobilePhoneNumber = response?.data?.mobilePhoneNumber ?: "",
                ),
        )

//    fun mapRefreshToken(response: ResData<String>?): Data<String> =
//        Data(
//            meta = mapMeta(response?.meta),
//            data = response?.data ?: "",
//        )

    fun mapLogin(response: ResData<LoginResponse>?): ApiData<LoginEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data =
                LoginEntity(
                    accessToken = response?.data?.accessToken ?: "",
                    refreshToken = response?.data?.refreshToken ?: "",
                    mobilePhoneNumber = response?.data?.mobilePhoneNumber ?: "",
                    user =
                        UserEntity(
                            userId = response?.data?.user?.userId ?: "",
                            phone = response?.data?.user?.phone ?: "",
                            role = response?.data?.user?.role ?: "",
                            createdAt = response?.data?.user?.createdAt ?: "",
                        ),
                ),
        )

    fun mapRefreshToken(response: ResData<LoginResponse>?): ApiData<LoginEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data =
                LoginEntity(
                    accessToken = response?.data?.accessToken ?: "",
                    refreshToken = response?.data?.refreshToken ?: "",
                    mobilePhoneNumber = response?.data?.mobilePhoneNumber ?: "",
                ),
        )

    fun mapStores(response: ResDataList<StoreResponse>?): ApiDataList<Store> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map { it.toEntity() },
        )

    fun mapStore(response: ResData<StoreResponse>?): ApiData<Store> =
        ApiData(
            meta = mapMeta(response?.meta),
            data = response?.data?.toEntity() ?: Store(),
        )

    fun mapCategory(response: ResDataList<CategoryResponse>?): ApiDataList<CategoryEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map { it.toEntity() },
        )

    fun mapSubCategory(response: ResDataList<SubCategoryResponse>?): ApiDataList<SubCategoryEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map {
                    SubCategoryEntity(
                        subCategoryId = it.subCategoryId,
                        storeId = it.storeId,
                        mainCategoryId = it.mainCategoryId,
                        name = it.name,
                        selected = it.selected,
                    )
                },
        )

    fun mapProducts(response: ResDataList<ProductResponse>?): ApiDataList<ProductEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data = response?.data ?.map { it.toEntity() },
        )

    fun mapMaterials(response: ResDataList<MaterialsResponse>?): ApiDataList<MaterialsEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data = response?.data ?.map { it.toEntity() },
        )

    fun mapSmartOrder(response: ResDataList<SmartOrderResponse>?): ApiDataList<SmartOrderEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data = response?.data ?.map { it.toEntity() },
        )

    fun mapOrder(response: ResData<OrderResponse>?): ApiData<OrderEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data = response?.data?.toEntity() ?: OrderEntity.EMPTY,
        )

    fun mapPayment(response: ResData<PaymentResponse>?): ApiData<PaymentEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data = response?.data?.toEntity() ?: PaymentEntity.EMPTY,
        )

    fun mapTables(response: ResDataList<TableResponse>?): ApiDataList<TableEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data = response?.data?.map { it.toEntity() },
        )

    // ----- Private extension functions -----

    private fun EmptyResponse.toEntity(): EmptyEntity =
        EmptyEntity(
            nothing = nothing ?: "",
        )

    private fun StoreResponse.toEntity(): Store =
        Store(
            storeId = storeId ?: 0,
            ownerID = ownerID ?: 0,
            cats =
                cats?.map {
                    Cats(
                        catId = it.catId ?: "",
                    )
                } ?: emptyList(),
            name = name ?: "",
            address = address ?: "",
            isActive = isActive ?: false,
        )

    private fun CategoryResponse.toEntity(): CategoryEntity =
        CategoryEntity(
            mainCategoryId = mainCategoryId ?: "",
            storeId = storeId ?: "",
            name = name ?: "",
            selected = selected ?: false,
        )

    private fun ProductResponse.toEntity(): ProductEntity =
        ProductEntity(
            productId = productId ?: "", // 추가
            storeId = storeId ?: "", // 추가
            mainCategoryId = mainCategoryId ?: "", // 추가
            subCategoryId = subCategoryId ?: "", // 추가
            name = name ?: "",
            descriptions = descriptions ?: "",
            isVat = isVat ?: true,
            selected = selected ?: false,
            stock = stock?.toEntity() ?: Stock("", false, 0),
            color = color ?: "#ffffff",
//            image = image ?: "",
            optionGroups = optionGroups?.map { it.toEntity() } ?: emptyList(),
            position = position ?: 0,
            imageUrl = imageUrl ?: "",
            basePrice = basePrice ?: "",
            isSoldOut = isSoldOut ?: false,
            isVatIncluded = isVatIncluded ?: true,
            barcode = barcode ?: "",
            dailyLimit = dailyLimit ?: 0,
            useStock = useStock ?: false,
            quantity = quantity ?: 1,
        )

    private fun PaymentResponse.toEntity(): PaymentEntity =
        PaymentEntity(
            pid = pid ?: "",
        )

    private fun MaterialsResponse.toEntity(): MaterialsEntity =
        MaterialsEntity(
            materialId = materialId ?: "",
            materialName = materialName ?: "",
            stock = stock ?: 0,
            safetyStock = safetyStock ?: 0,
            imageUrl = imageUrl ?: "",
            unit = unit ?: "",
        )

    private fun SmartOrderResponse.toEntity(): SmartOrderEntity =
        SmartOrderEntity(
            smartOrderId = smartOrderId ?: "",
            description = description ?: "",
            deliveryCost = deliveryCost ?: "",
            logisticsCompany = logisticsCompany ?: "",
            expectedConsumptionCount = expectedConsumptionCount ?: 0,
            origin = origin?.toEntity() ?: OriginEntity.EMPTY,
            material = material?.toEntity() ?: MaterialsEntity.EMPTY,
        )

    private fun OriginResponse.toEntity(): OriginEntity =
        OriginEntity(
            orignId = originId ?: "",
            name = name ?: "",
            price = price ?: "",
            deliveryCost = deliveryCost ?: 0,
            supplier = supplier ?: "",
        )

    private fun StockResponse.toEntity(): Stock =
        Stock(
            externalKey = externalKey ?: "",
            useStock = useStock ?: false,
            count = count ?: 0,
        )

    private fun OptionGroupsResponse.toEntity(): ProductOptionEntity =
        ProductOptionEntity(
            optionGroupId = optionGroupId ?: "",
            name = name ?: "",
            required = required ?: false,
            minOptionCountLimit = minOptionCountLimit ?: 0,
            maxOptionCountLimit = maxOptionCountLimit ?: 0,
            position = position ?: 0,
            options = options?.toEntities() ?: arrayListOf(),
        )

    private fun List<OptionResponse>?.toEntities(): ArrayList<OptionsEntity> =
        ArrayList(
            this?.map {
                OptionsEntity(
                    productOptionsId = it.productOptionsId ?: "",
                    name = it.name ?: "",
                    price = it.price ?: "",
                    position = it.position ?: 0,
                    useStock = it.useStock ?: false,
                    isSoldOut = it.isSoldOut ?: false,
                    isSelected = it.isSelected ?: false,
                    materials = it.materials?.map { it.toEntity() } ?: emptyList(),
                )
            } ?: emptyList(),
        )

    private fun OrderMemoResponse.toEntity(): OrderMemo =
        OrderMemo(
            externalKey = externalKey ?: "",
            oid = oid ?: "",
            content = content ?: "",
            createdAt = createdAt ?: "",
            updatedAt = updatedAt ?: "",
        )

    private fun TableResponse.toEntity(): TableEntity =
        TableEntity(
            tableExternalKey = tableExternalKey ?: "",
            tableNumber = tableNumber ?: 0,
            tableName = tableName ?: "",
            orders =
                orders?.map { it.toEntity() }?.let { ArrayList(it) } ?: arrayListOf(),
        )

    private fun OrderResponse.toEntity(): OrderEntity =
        OrderEntity(
            oid = oid ?: "",
            orderName = orderName ?: "",
            orderItems = orderItems?.map { it.toEntity() }?.let { ArrayList(it) } ?: arrayListOf(),
            buyerName = buyerName ?: "",
            orderFrom = orderFrom ?: OrderFrom.NONE,
            orderStatus = orderStatus ?: OrderStatus.NONE,
            paymentStatus = paymentStatus ?: PaymentStatus.NONE,
            tableNumber = tableNumber ?: 0,
            buyerPhoneNumber = buyerPhoneNumber ?: "",
            additionalComment = additionalComment ?: "",
            orderNumber = orderNumber ?: 0,
            orderMemos = orderMemos?.map { it.toEntity() }?.let { ArrayList(it) } ?: arrayListOf(),
        )
}
