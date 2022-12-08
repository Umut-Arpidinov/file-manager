package kg.o.internlabs.omarket.domain.repository

import kotlinx.coroutines.flow.Flow

interface PrefsRepository {

    fun saveNumberToPrefs(number: String)
    fun checkNumberFromPrefs(): Flow<String?>

    fun saveAccessTokenToPrefs(accessToken: String)
    fun getAccessTokenFromPrefs(): Flow<String?>

    fun saveRefTokenToPrefs(refToken: String)
    fun getRefTokenFromPrefs(): Flow<String?>

    fun setLoginStatusToPrefs(isLogged: Boolean)
    fun checkLoginStatusFromPrefs(): Flow<Boolean?>

    fun saveAvatarUrlToPrefs(url: String?)
    fun getAvatarUrlFromPrefs(): Flow<String?>

}