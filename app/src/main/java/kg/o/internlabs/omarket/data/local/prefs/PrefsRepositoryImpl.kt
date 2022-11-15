package kg.o.internlabs.omarket.data.local.prefs

import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.core.data.local.prefs.getBooleanFlow
import kg.o.internlabs.core.data.local.prefs.getStringFlow
import kg.o.internlabs.omarket.domain.repository.PrefsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(private val prefs: StoragePreferences) :
    PrefsRepository {

    override fun putNumberToPrefs(number: String) {
        prefs.msisdn = number
    }

    override fun checkNumberPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.MSISDN, "996243243243")

    override fun putPWDToPrefs(pwd: String) {
        prefs.password = pwd
    }

    override fun checkPWDFromPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.PASSWORD, "1234567890")

    override fun putAccessTokenToPrefs(accessToken: String) {
        prefs.token = accessToken
    }

    override fun getAccessTokenFromPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.ACCESS_TOKEN, "")

    override fun putRefTokenToPrefs(refToken: String) {
        prefs.refreshToken = refToken
    }

    override fun getRefTokenFromPrefs(): Flow<String?> =
        prefs.getStringFlow(StoragePreferences.Keys.REFRESH_TOKEN, "")

    override fun setLoginStatusPrefs(isLogged: Boolean) {
        prefs.isLoggedIn = isLogged
    }

    override fun getLoginStatusPrefs(): Flow<Boolean?> =
        prefs.getBooleanFlow(StoragePreferences.Keys.LOGIN_STATUS, false)
}

