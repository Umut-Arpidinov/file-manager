package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import javax.inject.Inject

class CheckNumberPrefs @Inject constructor(private val prefsRepository: PrefsRepository) {

    operator fun invoke()  = prefsRepository.checkPhoneNumberFromPrefs()

}