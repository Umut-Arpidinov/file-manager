package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class ResultXDto(
    @SerializedName("ad_type")
    val adType: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("author")
    val author: AuthorAdsDto? = null,
    @SerializedName("author_id")
    val authorId: Int? = null,
    @SerializedName("category")
    val category: CategoryAdsDto? = null,
    @SerializedName("commentary")
    val commentary: String? = null,
    @SerializedName("complaint_count")
    val complaintCount: Int? = null,
    @SerializedName("contract_price")
    val contractPrice: Boolean? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("currency_usd")
    val currencyUsd: Double? = null,
    @SerializedName("delivery")
    val delivery: Boolean? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("detail")
    val detail: Int? = null,
    @SerializedName("favorite")
    val favorite: Boolean? = null,
    @SerializedName("filters")
    val filters: List<Int>? = null,
    @SerializedName("has_image")
    val hasImage: Boolean? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("images")
    val images: List<String>? = null,
    @SerializedName("is_own")
    val isOwn: Boolean? = null,
    @SerializedName("latitude")
    val latitude: Any? = null,
    @SerializedName("location")
    val location: LocationXDto? = null,
    @SerializedName("longitude")
    val longitude: Any? = null,
    @SerializedName("minify_images")
    val minifyImages: List<String>? = null,
    @SerializedName("moderator_id")
    val moderatorId: Any? = null,
    @SerializedName("modified_at")
    val modifiedAt: String? = null,
    @SerializedName("num_of_views_in_feed")
    val numOfViewsInFeed: Int? = null,
    @SerializedName("o_money_pay")
    val oMoneyPay: Boolean? = null,
    @SerializedName("old_price")
    val oldPrice: String? = null,
    @SerializedName("opening_at")
    val openingAt: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("price_sort")
    val priceSort: String? = null,
    @SerializedName("promotion_type")
    val promotionType: PromotionTypeAdsDto? = null,
    @SerializedName("published_at")
    val publishedAt: String? = null,
    @SerializedName("removed_at")
    val removedAt: String? = null,
    @SerializedName("review_count")
    val reviewCount: Int? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("telegram_profile")
    val telegramProfile: String? = null,
    @SerializedName("telegram_profile_is_ident")
    val telegramProfileIsIdent: Boolean? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("view_count")
    val viewCount: Int? = null,
    @SerializedName("whatsapp_num")
    val whatsappNum: String? = null,
    @SerializedName("whatsapp_num_is_ident")
    val whatsappNumIsIdent: Boolean? = null
)