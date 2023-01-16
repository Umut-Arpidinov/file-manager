package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName


data class AdTypeDto(
    val details: String? = null,
    val errorCode: Int? = null,
    val results: List<AdTypeResultDto>? = null,
    val resultCode: String? = null
)

data class AdTypeResultDto (
    val id: Int? = null,
    @SerializedName("code_value")
    val codeValue: String? = null,
    val name: String? = null
)
