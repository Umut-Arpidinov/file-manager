package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.domain.entity.ads.AdsByCategory
import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class GetAdsUseCase  @Inject constructor(
    private val adsRep: AdsRepository
) {
    operator fun invoke(token: String, adsByCategory: AdsByCategory?) =
        adsRep.getAds(token, adsByCategory)
}