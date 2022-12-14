package kg.o.internlabs.omarket.data.remote.model

data class AvatarDto(
    val result: AvatarResultDto? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class AvatarResultDto (
    val url: String? = null
)

data class AvatarDelDto(
    val result: String? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)
