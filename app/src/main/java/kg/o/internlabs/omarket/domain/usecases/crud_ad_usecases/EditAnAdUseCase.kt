package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import kg.o.internlabs.omarket.domain.entity.EditAds
import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import javax.inject.Inject

class EditAnAdUseCase @Inject constructor(
    private val crudAdsRepository: CRUDAdsRepository
) {
    operator fun invoke(token: String, editAds: EditAds, uuid: String) =
        crudAdsRepository.editAnAd(token, editAds,  uuid)
}