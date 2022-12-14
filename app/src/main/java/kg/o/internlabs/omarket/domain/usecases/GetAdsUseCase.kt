package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class GetAdsUseCase  @Inject constructor(
    private val adsRep: AdsRepository
) {
    operator fun invoke(page: Int , token: String) = adsRep.getAds(page, token)
}