package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("ad_type")
    val adType: List<Int?>?,
    @SerializedName("category_type")
    val categoryType: String?,
    @SerializedName("dark_icon")
    val darkIcon: Any?,
    @SerializedName("dark_icon_img")
    val darkIconImg: String?,
    @SerializedName("delivery")
    val delivery: Boolean?,
    @SerializedName("filters")
    val filters: List<Any?>?,
    @SerializedName("has_dynamic_filter")
    val hasDynamicFilter: Any?,
    @SerializedName("has_map")
    val hasMap: Boolean?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("icon_img")
    val iconImg: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_popular")
    val isPopular: Boolean?,
    @SerializedName("linked_category")
    val linkedCategory: List<Any?>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order_num")
    val orderNum: Int?,
    @SerializedName("parent")
    val parent: Parent?,
    @SerializedName("parent_filters")
    val parentFilters: Boolean?,
    @SerializedName("required_price")
    val requiredPrice: Boolean?
)