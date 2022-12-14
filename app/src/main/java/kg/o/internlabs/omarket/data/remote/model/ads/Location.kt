package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location_type")
    val locationType: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("parent")
    val parent: Int?
)