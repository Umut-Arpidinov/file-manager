package kg.o.internlabs.omarket.domain.entity.ads

data class Author(
    val avatar: String? = null,
    val blockType: String? = null,
    val contactNumIsIdent: Boolean? = null,
    val contactNumber: String? = null,
    val hasPromotion: Boolean? = null,
    val id: Int? = null,
    val location: LocationAds? = null,
    val msisdn: String? = null,
    val partnerType: String? = null,
    val rating: Double? = null,
    val username: String? = null,
    val verified: Boolean? = null
)