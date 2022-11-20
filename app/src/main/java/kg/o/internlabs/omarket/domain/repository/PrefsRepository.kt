package kg.o.internlabs.omarket.domain.repository

import kotlinx.coroutines.flow.Flow

interface PrefsRepository {

    fun putNumberToPrefs(number: String)
    fun checkNumberPrefs(): Flow<String?>

    fun putPWDToPrefs(pwd: String)
    fun checkPWDFromPrefs(): Flow<String?>

    fun putAccessTokenToPrefs(accessToken: String)
    fun getAccessTokenFromPrefs(): Flow<String?>

    fun putRefTokenToPrefs(refToken: String)
    fun getRefTokenFromPrefs(): Flow<String?>

    fun setLoginStatusPrefs(isLogged: Boolean)
    fun getLoginStatusPrefs(): Flow<Boolean?>

}