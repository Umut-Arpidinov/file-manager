package kg.o.internlabs.omarket.data.remote.model

data class MainAdsDto(
    val id: Int, // ID of ad
    val minifyImages: List<String>? = null, //List of Images
    val name: String? = null, //Name of Product
    val price: String? = null,
    val oldPrice: String? = null,
    val category: String? = null,
    val location: String? = null, //City
    val isVIP: Boolean? = null, //Check for VIP status
    val isO_pay: Boolean? = null, //Check for O!money
    val currency: String? = null, //Som or Dollars
    val delivery: Boolean? = null, //Is Delivery available
    val favorite: Boolean? = null //In list of favorite
)