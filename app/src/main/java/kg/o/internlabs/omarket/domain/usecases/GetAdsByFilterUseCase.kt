package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.domain.entity.ads.AdsByFilter
import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class GetAdsByFilterUseCase  @Inject constructor(
    private val adsRep: AdsRepository
) {
    operator fun invoke(token: String, adsFilter: AdsByFilter?) =
        adsRep.getAdsFilter(token, adsFilter)
}