package kg.o.internlabs.omarket.domain.usecases.detailAd_use_case

import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class GetDetailAdUseCase @Inject constructor(private val adsRepository: AdsRepository) {
    operator fun invoke (token: String, uuid: String) = adsRepository.getDetailAd(token,uuid)
}