package kg.o.internlabs.omarket.domain.entity.ads

data class AdsByFilter(
    val mainFilters: MainFilters?
)


data class MainFilters(
    val q: String?
)