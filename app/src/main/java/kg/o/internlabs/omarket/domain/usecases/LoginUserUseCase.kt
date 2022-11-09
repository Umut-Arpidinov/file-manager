package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.repository.RegisterRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repo: RegisterRepository
) {
    operator fun invoke(reg: RegisterDto) = repo.loginUser(reg)
}