package kg.o.internlabs.omarket.domain.entity

data class FAQEntity (
    val count: Long? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: List<ResultsEntity>? = null
)

data class ResultsEntity (
    var id: Int? = null,
    var title: String? = null,
    var content: String? = null
)