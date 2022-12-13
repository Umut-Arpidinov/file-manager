package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class MainResultDto(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: Int?,
    @SerializedName("previous")
    val previous: Int?,
    @SerializedName("results")
    val results: List<ResultX>?
)