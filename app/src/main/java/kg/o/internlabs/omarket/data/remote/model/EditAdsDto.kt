package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.data.remote.model.ads.*

data class EditAdsDto(
    val details: String? = null,
    val errorCode: Int? = null,
    val result: ResultXDto? = null,
    val resultCode: String? = null,

    val address: String? = null,
    val category: Int? = null,
    @SerializedName("contract_price")
    val contractPrice: Boolean? = null,
    val currency: String? = null,
    val delivery: Boolean? = null,
    val description: String? = null,
    @SerializedName("ad_type")
    val adType: String? = null,
    val images: List<String>? = null,
    val latitude: String? = null,
    val location: Int? = null,
    val longitude: String? = null,
    @SerializedName("o_money_pay")
    val oMoneyPay: Boolean? = null,
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
    val title: String? = null
)