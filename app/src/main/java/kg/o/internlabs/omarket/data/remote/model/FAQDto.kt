package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName

data class FAQDto (
    val count: Long? = null,
    var next: String? = null,
    var previous: String? = null,
    @SerializedName("results")
    var results: List<ResultsDto>? = null
)

data class ResultsDto (
    var id: Int? = null,
    var title: String? = null,
    var content: String? = null
)