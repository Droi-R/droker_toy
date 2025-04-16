package com.bvc.data.remote.model.request

import com.bvc.domain.type.OrderStatus
import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("order") val order: OrderInfoRequest,
    @SerializedName("product_items") val productItems: List<ProductItemsRequest>,
)

data class OrderInfoRequest(
    @SerializedName("store_id") val storeId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("tables_id") val tablesId: Int,
    @SerializedName("order_from") val orderFrom: String,
    @SerializedName("order_status") val orderStatus: OrderStatus,
    @SerializedName("item_memo") val itemMemo: String,
    @SerializedName("total_price") val totalPrice: Int,
    @SerializedName("supply_price") val supplyPrice: Int,
    @SerializedName("vat_price") val vatPrice: Int,
    @SerializedName("discount_price") val discountPrice: Int,
    @SerializedName("final_price") val finalPrice: Int,
)
