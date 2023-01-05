package kg.o.internlabs.omarket.domain.entity.ads

data class LocationX(
    var id: Int? = null,  // id локаций
    var name: String? = null, // название локаций
    var locationType: String? = null, // тип локаций
    val orderNum: Int? = null,
    val isPopular: Boolean? = null,
    var parent: Int? = null, // к какому области относится id области
    val searchByName: String? = null,
    var subLocationsId: List<Int>? = arrayListOf() // если область все его города из картинки
)