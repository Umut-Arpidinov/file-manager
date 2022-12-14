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

data class FAQDto (
    val count: Long? = null,
    var next: String? = null,
    var previous: String? = null,
    @SerializedName("results")
    var results: List<ResultsDto>? = null
)

data class ResultsDto (
    var id: Int? = null,
    var title: String? = null,
    var content: String? = null
)

data class CategoriesDto(
    @SerializedName("result")
    val result: List<ResultDto>? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class ResultDto (
    var id: Int? = null,
    var parent: String? = null,
    var name: String? = null,
    @SerializedName("parent_filters")
    var parentFilters: Boolean? = null,
    @SerializedName("order_num")
    var orderNum: Int? = null,
    @SerializedName("is_popular")
    var isPopular: Boolean? = null,
    var delivery: Boolean? = null,
    @SerializedName("has_map")
    var hasMap: Boolean? = null,
    @SerializedName("required_price")
    var requiredPrice: Boolean? = null,
    @SerializedName("icon_img")
    var iconImg: String? = null,
    @SerializedName("dark_icon_img")
    var darkIconImg: String? = null,
    @SerializedName("category_type")
    var categoryType: String? = null,
    @SerializedName("linked_category")
    var linkedCategory: List<String>? = null,
    var filters: List<String>? = null,
    @SerializedName("ad_type")
    var adType: List<Int>? = null,
    @SerializedName("has_dynamic_filter")
    var hasDynamicFilter: Boolean? = null,
    @SerializedName("sub_categories")
    var subCategories: List<SubCategoriesDto>? = null
)

data class SubCategoriesDto (
    var id: Int? = null,
    var parent: Int? = null,
    var name: String? = null,
    @SerializedName("parent_filters")
    var parentFilters: Boolean? = null,
    var delivery: Boolean? = null,
    @SerializedName("order_num")
    var orderNum: Int? = null,
    @SerializedName("is_popular")
    var isPopular: Boolean? = null,
    @SerializedName("has_map")
    var hasMap: Boolean? = null,
    @SerializedName("required_price")
    var requiredPrice: Boolean? = null,
    @SerializedName("icon_img")
    var iconImg: String? = null,
    @SerializedName("dark_icon_img")
    var darkIconImg: String? = null,
    @SerializedName("category_type")
    var categoryType: String? = null,
    @SerializedName("linked_category")
    var linkedCategory: List<String>? = null,
    var filters: List<Int>? = null,
    @SerializedName("ad_type")
    var adType: List<Int>? = null,
    @SerializedName("has_dynamic_filter")
    var hasDynamicFilter: Boolean? = null,
    @SerializedName("sub_categories")
    var subCategories: List<String>? = null
)
data class AvatarDto(
    val result: AvatarResultDto? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class AvatarResultDto (
    val url: String? = null
)

data class AvatarDelDto(
    val result: String? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)