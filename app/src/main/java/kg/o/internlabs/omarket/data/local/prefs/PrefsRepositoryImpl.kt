package kg.o.internlabs.omarket.data.local.prefs

import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(private val context: StoragePreferences) : PrefsRepository {

    override fun checkPhoneNumberFromPrefs(number: String): Boolean {
        val phoneNumber = context.userPhoneNumber
        return phoneNumber?.isNotEmpty() ?: false
    }
}