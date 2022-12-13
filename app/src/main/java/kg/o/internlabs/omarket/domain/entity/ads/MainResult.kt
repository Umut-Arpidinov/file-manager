package kg.o.internlabs.omarket.domain.entity.ads

data class MainResult(
    val count: Int? = null,
    val next: Int? = null,
    val previous: Int? = null,
    val results: List<ResultX>? = null
)