package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class AdTypeUseCase @Inject constructor(
    private val adsRepository: AdsRepository
) {
    operator fun invoke(token: String) =
        adsRepository.getAdType(token)
}