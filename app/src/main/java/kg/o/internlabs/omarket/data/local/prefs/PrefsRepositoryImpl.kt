package kg.o.internlabs.omarket.data.local.prefs

import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.core.data.local.prefs.getBooleanFlow
import kg.o.internlabs.core.data.local.prefs.getStringFlow
import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(private val prefs: StoragePreferences) :
    PrefsRepository {

    override fun saveNumberToPrefs(number: String) {
        prefs.msisdn = number
    }

    override fun checkNumberFromPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.MSISDN, "")

    override fun saveAccessTokenToPrefs(accessToken: String) {
        prefs.token = accessToken
    }

    override fun getAccessTokenFromPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.ACCESS_TOKEN, "")

    override fun saveRefTokenToPrefs(refToken: String) {
        prefs.refreshToken = refToken
    }

    override fun getRefTokenFromPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.REFRESH_TOKEN, "")

    override fun setLoginStatusToPrefs(isLogged: Boolean) {
        prefs.isLoggedIn = isLogged
    }

    override fun checkLoginStatusFromPrefs(): Flow<Boolean?> =
        prefs.getBooleanFlow(StoragePreferences.Keys.LOGIN_STATUS, false)
}

