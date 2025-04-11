package com.bvc.data.mapper

import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.GithubResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.OptionResponse
import com.bvc.data.remote.model.response.OrderMemoResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductOptionResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.ResMeta
import com.bvc.data.remote.model.response.ResPagination
import com.bvc.data.remote.model.response.StockResponse
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.remote.model.response.TableResponse
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Meta
import com.bvc.domain.model.Options
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.OrderMemo
import com.bvc.domain.model.Pagination
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity
import com.bvc.domain.model.Stock
import com.bvc.domain.model.Store
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.model.UserEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus

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

    fun mapStore(response: ResDataList<StoreResponse>?): ApiDataList<Store> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map { it.toEntity() },
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
                        id = it.id,
                        name = it.name,
                        selected = it.selected,
                    )
                },
        )

    fun mapProducts(response: ResDataList<ProductResponse>?): ApiDataList<ProductEntity> =
        ApiDataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data = response?.data?.map { it.toEntity() },
        )

    fun mapOrder(response: ResData<OrderResponse>?): ApiData<OrderEntity> =
        ApiData(
            meta = mapMeta(response?.meta),
            data = response?.data?.toEntity() ?: OrderEntity(),
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
            tid = tid ?: "",
            name = name ?: "",
            address = address ?: "",
            isActive = isActive ?: 0,
        )

    private fun CategoryResponse.toEntity(): CategoryEntity =
        CategoryEntity(
            id = id ?: "",
            name = name ?: "",
            selected = selected ?: false,
        )

    private fun ProductResponse.toEntity(): ProductEntity =
        ProductEntity(
            externalKey = externalKey ?: "",
            name = name ?: "",
            categoryKey = categoryKey ?: "",
            categoryName = categoryName ?: "",
            descriptions = descriptions ?: "",
            isVat = isVat ?: true,
            selected = selected ?: false,
            stock = stock?.toEntity() ?: Stock(),
            color = color ?: "#ffffff",
            image = image ?: "",
            price = price ?: "",
            productOption = productOption?.map { it.toEntity() } ?: emptyList(),
            position = position ?: 0,
        )

    private fun StockResponse.toEntity(): Stock =
        Stock(
            externalKey = externalKey ?: "",
            useStock = useStock ?: false,
            count = count ?: 0,
        )

    private fun ProductOptionResponse.toEntity(): ProductOptionEntity =
        ProductOptionEntity(
            id = id ?: "",
            name = name ?: "",
            required = required ?: "",
            minOptionCountLimit = minOptionCountLimit ?: 0,
            maxOptionCountLimit = maxOptionCountLimit ?: 0,
            position = position ?: 0,
            options = options?.toEntities() ?: arrayListOf(),
        )

    private fun List<OptionResponse>?.toEntities(): ArrayList<Options> =
        ArrayList(
            this?.map {
                Options(
                    id = it.id ?: "",
                    name = it.name ?: "",
                    price = it.price ?: "",
                    position = it.position ?: 0,
                    useStock = it.useStock ?: false,
                    stockQuantity = it.stockQuantity ?: -1,
                    isSoldOut = it.isSoldOut ?: false,
                    isSelected = it.isSelected ?: false,
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
            orderFrom = orderFrom ?: OrderFrom.POS,
            orderStatus = orderStatus ?: OrderStatus.READY,
            tableNumber = tableNumber ?: 0,
            buyerPhoneNumber = buyerPhoneNumber ?: "",
            additionalComment = additionalComment ?: "",
            orderNumber = orderNumber ?: 0,
            orderMemos = orderMemos?.map { it.toEntity() }?.let { ArrayList(it) } ?: arrayListOf(),
        )
}
