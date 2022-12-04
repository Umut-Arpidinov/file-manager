package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName

data class MyAdsDto(
    val count: Long? = null,
    val next: String? = null,
    val previous: String? = null,
    @SerializedName("result")
    val result: MyAdsResultDto? = null,
    val statuses: List<String>? = null
)

data class MyAdsResultDto(
    val next: String? = null,
    val previous: String? = null,
    val count: Long? = null,
    @SerializedName("results")
    val results: List<MyAdsResultsDto>? = null
)

data class MyAdsResultsDto(
    @SerializedName("promotion_type")
    val promotionType: PromotionTypeDto? = null,
    val address: String? = null,
    @SerializedName("author")
    val author: AuthorDto? = null,
    val latitude: String? = null,
    @SerializedName("currency_usd")
    val currencyUsd: Double? = null,
    val description: String? = null,
    @SerializedName("minify_images")
    val minifyImages: List<String>? = null,
    val title: String? = null,
    @SerializedName("contract_price")
    val contractPrice: Boolean? = null,
    val uuid: String? = null,
    @SerializedName("o_money_pay")
    val oMoneyPay: Boolean? = null,
    val price: String? = null,
    val currency: String? = null,
    val location: LocationDto? = null,
    val id: Int? = null,
    @SerializedName("modified_at")
    val modifiedAt: String? = null,
    val category: CategoryDto? = null,
    val favorite: Boolean? = null,
    @SerializedName("view_count")
    val viewCount: String? = null,
    val longitude: String? = null,
    val status: String? = null,
    @SerializedName("is_own")
    val isOwn: Boolean? = null
)

data class CategoryDto(
    val name: String? = null
)

data class LocationDto(
    val name: String? = null
)

data class AuthorDto(
    val verified: Boolean? = null
)

data class PromotionTypeDto(
    val type: String? = null
)