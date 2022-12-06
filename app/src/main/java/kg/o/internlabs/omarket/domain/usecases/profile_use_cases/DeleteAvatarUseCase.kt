package kg.o.internlabs.omarket.domain.usecases.profile_use_cases

import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteAvatarUseCase @Inject constructor(
    private val profileRep: ProfileRepository
) {
    operator fun invoke(token: String ) = profileRep.deleteAvatar(token)
}