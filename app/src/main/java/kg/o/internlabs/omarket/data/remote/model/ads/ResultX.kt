package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class ResultX(
    @SerializedName("ad_type")
    val adType: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("author")
    val author: Author?,
    @SerializedName("author_id")
    val authorId: Int?,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("commentary")
    val commentary: String?,
    @SerializedName("complaint_count")
    val complaintCount: Int?,
    @SerializedName("contract_price")
    val contractPrice: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("currency_usd")
    val currencyUsd: Double?,
    @SerializedName("delivery")
    val delivery: Boolean?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("detail")
    val detail: Int?,
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("filters")
    val filters: List<Any>?,
    @SerializedName("has_image")
    val hasImage: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("is_own")
    val isOwn: Boolean?,
    @SerializedName("latitude")
    val latitude: Any?,
    @SerializedName("location")
    val location: LocationX?,
    @SerializedName("longitude")
    val longitude: Any?,
    @SerializedName("minify_images")
    val minifyImages: List<String>?,
    @SerializedName("moderator_id")
    val moderatorId: Any?,
    @SerializedName("modified_at")
    val modifiedAt: String?,
    @SerializedName("num_of_views_in_feed")
    val numOfViewsInFeed: Int?,
    @SerializedName("o_money_pay")
    val oMoneyPay: Boolean?,
    @SerializedName("old_price")
    val oldPrice: String?,
    @SerializedName("opening_at")
    val openingAt: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("price_sort")
    val priceSort: String?,
    @SerializedName("promotion_type")
    val promotionType: PromotionType?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("removed_at")
    val removedAt: Any?,
    @SerializedName("review_count")
    val reviewCount: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("telegram_profile")
    val telegramProfile: String?,
    @SerializedName("telegram_profile_is_ident")
    val telegramProfileIsIdent: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("view_count")
    val viewCount: Int?,
    @SerializedName("whatsapp_num")
    val whatsappNum: String?,
    @SerializedName("whatsapp_num_is_ident")
    val whatsappNumIsIdent: Boolean?
)