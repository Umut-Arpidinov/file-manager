package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class LocationAdsDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("location_type")
    val locationType: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("parent")
    val parent: Int? = null
)