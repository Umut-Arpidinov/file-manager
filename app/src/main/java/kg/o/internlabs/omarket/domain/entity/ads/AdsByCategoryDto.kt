package kg.o.internlabs.omarket.domain.entity.ads

data class AdsByCategory(
    val details: String? = null,
    val errorCode: Int? = null,
    val resultL: List<MainResult>? = null,
    val resultCode: String? = null,
    val q: String? = null
)