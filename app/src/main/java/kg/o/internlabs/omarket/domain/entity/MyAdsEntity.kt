package kg.o.internlabs.omarket.domain.entity

import com.google.gson.annotations.SerializedName

data class MyAdsEntity (
    @SerializedName("result")
    val result: MyAdsResultEntity? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null,
    val statuses: List<String>? = null
)

data class MyAdsResultEntity(
    val next: String? = null,
    val previous: String? = null,
    val count: Long? = null,
    val results: List<MyAdsResultsEntity>? = null
)

data class MyAdsResultsEntity(
    val promotionType: PromotionTypeEntity? = null,
    val address: String? = null,
    val author: AuthorEntity? = null,
    val latitude: String? = null,
    val currencyUsd: Double? = null,
    val description: String? = null,
    val minifyImages: List<String>? = null,
    val title: String? = null,
    val contractPrice: Boolean? = null,
    val uuid: String? = null,
    val oMoneyPay: Boolean? = null,
    val price: String? = null,
    val oldPrice: String? = null,
    val currency: String? = null,
    val location: LocationEntity? = null,
    val id: Int? = null,
    val modifiedAt: String? = null,
    val category: CategoryEntity? = null,
    val favorite: Boolean? = null,
    val viewCount: String? = null,
    val longitude: String? = null,
    val status: String? = null,
    val isOwn: Boolean? = null
)

data class CategoryEntity(
    val name: String? = null,
    val delivery: Boolean? = null
)

data class LocationEntity(
    val name: String? = null
)

data class AuthorEntity(
    val verified: Boolean? = null
)

data class PromotionTypeEntity(
    val type: String? = null
)