package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName

data class AdTypeDto(
    val details: String? = null,
    val errorCode: Int? = null,
    val result: AdTypeResultDto? = null,
    val resultCode: String? = null
)

data class AdTypeResultDto (
    val count: Int? = null,
    val results: List<AdTypeResultsDto>? = null
)
data class AdTypeResultsDto (
    val id: Int? = null,
    @SerializedName("code_value")
    val codeValue: String? = null,
    val name: String? = null
)
