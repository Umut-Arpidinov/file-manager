package kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases

import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import javax.inject.Inject

class InitiateAdUseCase @Inject constructor(
    private val crudAdsRepository: CRUDAdsRepository
) {
    operator fun invoke(token: String ) = crudAdsRepository.getInitiatedAnAD(token)
}