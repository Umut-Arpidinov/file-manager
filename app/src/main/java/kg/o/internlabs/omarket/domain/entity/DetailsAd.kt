package kg.o.internlabs.omarket.domain.entity

import kg.o.internlabs.omarket.domain.entity.ads.ResultX

data class DetailsAd(
    val details: String? = null,
    val errorCode: Int? = null,
    val resultX: ResultX? = null,
    val resultCode: String? = null,
)