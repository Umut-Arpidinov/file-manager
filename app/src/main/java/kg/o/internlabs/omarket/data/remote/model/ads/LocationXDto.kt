package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class LocationXDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_popular")
    val isPopular: Boolean? = null,
    @SerializedName("location_type")
    val locationType: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("order_num")
    val orderNum: Int? = null,
    @SerializedName("parent")
    val parent: Int? = null,
    @SerializedName("search_by_name")
    val searchByName: String? = null
)