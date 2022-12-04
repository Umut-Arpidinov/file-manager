package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.repository.RegisterRepository
import javax.inject.Inject

class CheckOtpUseCase @Inject constructor(
    private val repo: RegisterRepository
) {
    operator fun invoke(reg: RegisterEntity) = repo.checkOtp(reg)
}