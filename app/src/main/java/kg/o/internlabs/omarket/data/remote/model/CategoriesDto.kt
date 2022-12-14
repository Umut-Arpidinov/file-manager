package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName

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