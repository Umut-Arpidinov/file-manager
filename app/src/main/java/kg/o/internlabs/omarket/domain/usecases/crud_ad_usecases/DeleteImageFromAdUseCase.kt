package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import kg.o.internlabs.omarket.domain.entity.DeletedImageUrlEntity
import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import javax.inject.Inject

class DeleteImageFromAdUseCase @Inject constructor(
    private val crudAdsRepository: CRUDAdsRepository
) {
    operator fun invoke(token: String, body: DeletedImageUrlEntity,  uuid: String) =
        crudAdsRepository.deleteImageFromAd(token, body, uuid)
}