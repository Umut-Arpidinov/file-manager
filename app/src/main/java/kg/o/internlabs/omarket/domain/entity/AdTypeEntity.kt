package kg.o.internlabs.omarket.domain.entity


data class AdTypeEntity(
    val details: String? = null,
    val errorCode: Int? = null,
    val results: List<AdTypeResultEntity>? = null,
    val resultCode: String? = null
)

data class AdTypeResultEntity (
    val id: Int? = null,
    val codeValue: String? = null,
    val name: String? = null
)
