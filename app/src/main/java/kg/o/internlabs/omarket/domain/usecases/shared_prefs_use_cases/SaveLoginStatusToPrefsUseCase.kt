package kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases

import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import javax.inject.Inject

class SaveLoginStatusToPrefsUseCase @Inject constructor(private val prefsRepository: PrefsRepository) {

    operator fun invoke(isLogged: Boolean)  = prefsRepository.setLoginStatusToPrefs(isLogged)

}