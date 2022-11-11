package kg.o.internlabs.omarket.data.local.prefs

import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.core.data.local.prefs.getBooleanFlow
import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(private val context: StoragePreferences) : PrefsRepository {

    override fun checkPhoneNumberFromPrefs(number: String): Flow<Boolean> {
        val phoneNumber = context.userPhoneNumber
        if (phoneNumber == number){
            return context.getBooleanFlow(StoragePreferences.Keys.USER_PHONE_NUMBER, false)
        }
        context.emergency = false
        return context.getBooleanFlow(StoragePreferences.Keys.EMERGENCY, false)
    }

}