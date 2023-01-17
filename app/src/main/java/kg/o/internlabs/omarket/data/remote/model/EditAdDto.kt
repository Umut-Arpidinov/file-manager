package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.data.remote.model.ads.*

data class EditAdDto(
    val category: Int? = null,
    @SerializedName("contract_price")
    val contractPrice: Boolean? = null,
    val currency: String? = null,
    val delivery: Boolean? = null,
    val description: String? = null,
    @SerializedName("ad_type")
    val adType: String? = null,
    val images: List<String>? = null,
    val location: Int? = null,
    @SerializedName("o_money_pay")
    val oMoneyPay: Boolean? = null,
    val price: String? = null,
    @SerializedName("telegram_profile")
    val telegramProfile: String? = null,
    @SerializedName("whatsapp_num")
    val whatsappNum: String? = null,
    @SerializedName("whatsapp_num_is_ident")
    val title: String? = null
)