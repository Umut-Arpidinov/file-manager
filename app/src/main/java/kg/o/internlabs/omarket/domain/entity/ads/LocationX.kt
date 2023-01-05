package kg.o.internlabs.omarket.domain.entity.ads

data class LocationX(
    var id: Int? = null,
    var name: String? = null,
    var locationType: String? = null,
    var parent: Int? = null,
    var subLocationsId: List<Int>? = null
)