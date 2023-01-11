package kg.o.internlabs.omarket.domain.entity

data class DeleteEntity(
    val result: String? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null,
    val url: String? = null
)