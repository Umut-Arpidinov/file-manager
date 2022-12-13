package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class LocationX(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_popular")
    val isPopular: Boolean?,
    @SerializedName("location_type")
    val locationType: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order_num")
    val orderNum: Int?,
    @SerializedName("parent")
    val parent: Int?,
    @SerializedName("search_by_name")
    val searchByName: String?
)