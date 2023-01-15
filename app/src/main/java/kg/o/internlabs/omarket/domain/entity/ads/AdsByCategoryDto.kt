package kg.o.internlabs.omarket.domain.entity.ads

data class AdsByCategory(
    val mainFilter: MainFilter? = null
)

data class MainFilter(
    val orderBy: String? = null,
    val categoryId: Int? = null,
    val q: String? = null
)