package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.data.remote.model.Register
import kg.o.internlabs.omarket.domain.repository.CheckOtpRepository
import javax.inject.Inject

class CheckOtpUseCase @Inject constructor(
    private val repo: CheckOtpRepository
) {
     operator fun invoke(reg: Register) = repo.checkOtp(reg)
}