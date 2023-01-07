package kg.o.internlabs.omarket.domain.entity

import kg.o.internlabs.omarket.domain.entity.ads.PromotionType
import kg.o.internlabs.omarket.domain.entity.ads.ResultX

data class EditAds(
    val details: String? = null,
    val errorCode: Int? = null,
    val result: ResultX? = null,
    val resultCode: String? = null,

    val category: Int? = null,
    val address: String? = null,
    val contractPrice: Boolean? = null,
    val currency: String? = null,
    val delivery: Boolean? = null,
    val description: String? = null,
    val adType: String? = null,
    val images: List<String>? = null,
    val latitude: String? = null,
    val location: Int? = null,
    val longitude: String? = null,
    val oMoneyPay: Boolean? = null,
    val price: String? = null,
    val promotionType: PromotionType? = null,
    val telegramProfile: String? = null,
    val telegramProfileIsIdent: Boolean? = null,
    val whatsappNum: String? = null,
    val whatsappNumIsIdent: Boolean? = null,
    val title: String? = null
)