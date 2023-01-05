package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.data.remote.model.ads.*

data class EditAdsDto(
    @SerializedName("details")
    val details: String? = null,
    @SerializedName("errorCode")
    val errorCode: Int? = null,
    @SerializedName("result")
    val result: ResultXDto? = null,
    @SerializedName("resultCode")
    val resultCode: String? = null,

    @SerializedName("category")
    val category: Int? = null,
    @SerializedName("contract_price")
    val contractPrice: Boolean? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("delivery")
    val delivery: Boolean? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("ad_type")
    val adType: String? = null,
    @SerializedName("images")
    val images: List<String>? = null,
    @SerializedName("latitude")
    val latitude: String? = null,
    @SerializedName("location")
    val location: Int? = null,
    @SerializedName("longitude")
    val longitude: String? = null,
    @SerializedName("o_money_pay")
    val oMoneyPay: Boolean? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("promotion_type")
    val promotionType: PromotionTypeAdsDto? = null,
    @SerializedName("telegram_profile")
    val telegramProfile: String? = null,
    @SerializedName("telegram_profile_is_ident")
    val telegramProfileIsIdent: Boolean? = null,
    @SerializedName("whatsapp_num")
    val whatsappNum: String? = null,
    @SerializedName("whatsapp_num_is_ident")
    val whatsappNumIsIdent: Boolean? = null,
    @SerializedName("title")
    val title: String? = null
)