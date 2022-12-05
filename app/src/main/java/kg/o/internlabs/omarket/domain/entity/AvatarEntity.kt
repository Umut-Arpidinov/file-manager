package kg.o.internlabs.omarket.domain.entity

data class AvatarEntity(
    val result: AvatarResultEntity? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)

data class AvatarResultEntity (
    val url: String? = null
)

data class AvatarDelEntity(
    val result: String? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null
)