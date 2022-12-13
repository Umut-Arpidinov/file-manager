package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class MainResultDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: Int? = null,
    @SerializedName("previous")
    val previous: Int? = null,
    @SerializedName("results")
    val results: List<ResultXDto>? = null
)