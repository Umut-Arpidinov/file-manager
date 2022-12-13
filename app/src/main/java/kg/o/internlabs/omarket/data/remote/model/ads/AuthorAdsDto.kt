package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class AuthorAdsDto(
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("block_type")
    val blockType: String? = null,
    @SerializedName("contact_num_is_ident")
    val contactNumIsIdent: Boolean? = null,
    @SerializedName("contact_number")
    val contactNumber: String? = null,
    @SerializedName("has_promotion")
    val hasPromotion: Boolean? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("location")
    val location: LocationAdsDto? = null,
    @SerializedName("msisdn")
    val msisdn: String? = null,
    @SerializedName("partner_type")
    val partnerType: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("verified")
    val verified: Boolean? = null
)