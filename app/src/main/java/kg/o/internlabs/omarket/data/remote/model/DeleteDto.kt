package kg.o.internlabs.omarket.data.remote.model

data class DeleteDto(
    val result: String? = null,
    val resultCode: String? = null,
    val details: String? = null,
    val errorCode: Int? = null,
    val url: String? = null
)