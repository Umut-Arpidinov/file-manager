package kg.o.internlabs.omarket.data.remote.model

data class UploadImageDto(
    val result: UploadImageResultDto? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class UploadImageResultDto (
    val url: String? = null
)
