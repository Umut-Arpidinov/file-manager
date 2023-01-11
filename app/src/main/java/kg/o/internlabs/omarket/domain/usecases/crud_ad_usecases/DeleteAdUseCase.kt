package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import javax.inject.Inject

class DeleteAdUseCase @Inject constructor(
    private val crudAdsRepository: CRUDAdsRepository
) {
    operator fun invoke(token: String, uuid: String) =
        crudAdsRepository.deleteAd(token, uuid)
}