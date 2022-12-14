package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("block_type")
    val blockType: String?,
    @SerializedName("contact_num_is_ident")
    val contactNumIsIdent: Boolean?,
    @SerializedName("contact_number")
    val contactNumber: String?,
    @SerializedName("has_promotion")
    val hasPromotion: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("msisdn")
    val msisdn: String?,
    @SerializedName("partner_type")
    val partnerType: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("verified")
    val verified: Boolean?
)