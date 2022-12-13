package kg.o.internlabs.omarket.domain.entity.ads

data class CategoryAds(
    val adType: List<Int?>? = null,
    val categoryType: String? = null,
    val darkIcon: String? = null,
    val darkIconImg: String? = null,
    val delivery: Boolean? = null,
    val filters: List<Int?>? = null,
    val hasDynamicFilter: Boolean? = null,
    val hasMap: Boolean? = null,
    val icon: String? = null,
    val iconImg: String? = null,
    val id: Int? = null,
    val isPopular: Boolean? = null,
    val linkedCategory: List<Int?>? = null,
    val name: String? = null,
    val orderNum: Int? = null,
    val parent: ParentAds? = null,
    val parentFilters: Boolean? = null,
    val requiredPrice: Boolean? = null
)