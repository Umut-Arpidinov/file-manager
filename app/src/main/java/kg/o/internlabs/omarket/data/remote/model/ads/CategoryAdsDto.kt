package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class CategoryAdsDto(
    @SerializedName("ad_type")
    val adType: List<Int?>? = null,
    @SerializedName("category_type")
    val categoryType: String? = null,
    @SerializedName("dark_ico")
    val darkIcon: String? = null,
    @SerializedName("dark_icon_img")
    val darkIconImg: String? = null,
    @SerializedName("delivery")
    val delivery: Boolean? = null,
    @SerializedName("filters")
    val filters: List<Int?>? = null,
    @SerializedName("has_dynamic_filter")
    val hasDynamicFilter: Boolean? = null,
    @SerializedName("has_map")
    val hasMap: Boolean? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("icon_img")
    val iconImg: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_popular")
    val isPopular: Boolean? = null,
    @SerializedName("linked_category")
    val linkedCategory: List<Int?>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("order_num")
    val orderNum: Int? = null,
    @SerializedName("parent")
    val parent: ParentAdsDto? = null,
    @SerializedName("parent_filters")
    val parentFilters: Boolean? = null,
    @SerializedName("required_price")
    val requiredPrice: Boolean? = null
)