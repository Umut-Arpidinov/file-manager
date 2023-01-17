package kg.o.internlabs.omarket.domain.entity

data class AdTypeEntity(
    val details: String? = null,
    val errorCode: Int? = null,
    val result: AdTypeResultEntity? = null,
    val resultCode: String? = null
)

data class AdTypeResultEntity (
    val count: Int? = null,
    val results: List<AdTypeResultsEntity>? = null
)

data class AdTypeResultsEntity (
    val id: Int? = null,
    val codeValue: String? = null,
    val name: String? = null
)
