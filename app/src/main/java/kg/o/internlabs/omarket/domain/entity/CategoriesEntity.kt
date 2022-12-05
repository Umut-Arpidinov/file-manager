package kg.o.internlabs.omarket.domain.entity

data class CategoriesEntity(
    val result: List<ResultEntity>? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class ResultEntity (
    var id: Int? = null,
    var parent: String? = null,
    var name: String? = null,
    var parentFilters: Boolean? = null,
    var orderNum: Int? = null,
    var isPopular: Boolean? = null,
    var delivery: Boolean? = null,
    var hasMap: Boolean? = null,
    var requiredPrice: Boolean? = null,
    var iconImg: String? = null,
    var darkIconImg: String? = null,
    var categoryType: String? = null,
    var linkedCategory: List<String>? = null,
    var filters: List<String>? = null,
    var adType: List<Int>? = null,
    var hasDynamicFilter: Boolean? = null,
    var subCategories: List<SubCategoriesEntity>? = null
)

data class SubCategoriesEntity (
    var id: Int? = null,
    var parent: Int? = null,
    var name: String? = null,
    var parentFilters: Boolean? = null,
    var delivery: Boolean? = null,
    var orderNum: Int? = null,
    var isPopular: Boolean? = null,
    var hasMap: Boolean? = null,
    var requiredPrice: Boolean? = null,
    var iconImg: String? = null,
    var darkIconImg: String? = null,
    var categoryType: String? = null,
    var linkedCategory: List<String>? = null,
    var filters: List<Int>? = null,
    var adType: List<Int>? = null,
    var hasDynamicFilter: Boolean? = null,
    var subCategories: List<String>? = null
)