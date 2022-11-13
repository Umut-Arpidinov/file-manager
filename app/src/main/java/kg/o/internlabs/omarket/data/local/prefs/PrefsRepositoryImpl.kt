package kg.o.internlabs.omarket.data.local.prefs

import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.core.data.local.prefs.getStringFlow
import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(private val prefs: StoragePreferences) :
    PrefsRepository {

    override fun checkPhoneNumberFromPrefs(): Flow<String?> {
        return prefs.getStringFlow(StoragePreferences.Keys.USER_PHONE_NUMBER, "996243243243")
    }
}

