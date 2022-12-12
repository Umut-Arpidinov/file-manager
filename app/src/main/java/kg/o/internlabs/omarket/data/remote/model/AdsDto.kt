package kg.o.internlabs.omarket.data.remote.model

data class AdsDto(
    val details: String?,
    val errorCode: Int?,
    val result: Result?,
    val resultCode: String?
)

data class Author(
    val avatar: String?,
    val block_type: String?,
    val contact_number: String?,
    val id: Int?,
    val location: Location?,
    val msisdn: String?,
    val rating: Double?,
    val username: String?,
    val verified: Boolean?
)

data class Category(
    val category_type: String?,
    val dark_icon: Any?,
    val dark_icon_img: String?,
    val delivery: Boolean?,
    val filters: List<Int?>?,
    val has_dynamic_filter: Any?,
    val has_map: Boolean?,
    val icon: Any?,
    val icon_img: String?,
    val id: Int?,
    val is_popular: Boolean?,
    val linked_category: List<Any?>?,
    val name: String?,
    val order_num: Int?,
    val parent: Any?,
    val parent_filters: Boolean?,
    val required_price: Boolean?
)

data class Location(
    val id: Int?,
    val location_type: String?,
    val name: String?,
    val parent: Int?
)

data class LocationX(
    val id: Int?,
    val is_popular: Boolean?,
    val location_type: String?,
    val name: String?,
    val order_num: Int?,
    val parent: Int?,
    val search_by_name: String?
)

data class PromotionType(
    val description: String?,
    val id: Int?,
    val img: String?,
    val type: String?
)

data class Result(
    val count: Int?,
    val next: Int?,
    val previous: Any?,
    val results: List<ResultX>?
)

data class ResultX(
    val ad_type: String?,
    val address: String?,
    val author: Author?,
    val author_id: Int?,
    val category: Category?,
    val commentary: String?,
    val complaint_count: Int?,
    val contract_price: Boolean?,
    val created_at: String?,
    val currency: String?,
    val currency_usd: Double?,
    val delivery: Boolean?,
    val description: String?,
    val detail: Int?,
    val favorite: Boolean?,
    val filters: List<Any>?,
    val has_image: Boolean?,
    val id: Int?,
    val images: List<Any>?,
    val is_own: Boolean?,
    val latitude: Any?,
    val location: LocationX?,
    val longitude: Any?,
    val minify_images: List<String>?,
    val moderator_id: Any?,
    val modified_at: String?,
    val o_money_pay: Boolean?,
    val old_price: String?,
    val opening_at: String?,
    val price: String?,
    val price_sort: String?,
    val promotion_type: PromotionType?,
    val published_at: Any?,
    val removed_at: Any?,
    val review_count: Int?,
    val status: String?,
    val telegram_profile: String?,
    val telegram_profile_is_ident: Boolean?,
    val title: String?,
    val uuid: String?,
    val view_count: Int?,
    val whatsapp_num: String?,
    val whatsapp_num_is_ident: Boolean?
)