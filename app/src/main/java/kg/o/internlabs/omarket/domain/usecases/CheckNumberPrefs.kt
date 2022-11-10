package kg.o.internlabs.omarket.domain.usecases

import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import javax.inject.Inject

class CheckNumberPrefs @Inject constructor(private val prefsRepository: PrefsRepository) {

    fun checkPhoneNumberFromPrefs(number: String)  = prefsRepository.checkPhoneNumberFromPrefs(number)

}