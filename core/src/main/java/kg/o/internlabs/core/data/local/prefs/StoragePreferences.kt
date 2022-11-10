package kg.o.internlabs.core.data.local.prefs

import android.content.Context

class StoragePreferences(context: Context) : BasePrefs(context) {

    override val prefFileName: String
        get() = "kg.o.internlabs.core.data.local.prefs"

    var token: String? by PrefDelegate(sharedPreference, Keys.ACCESS_TOKEN, "")
    var refreshToken: String? by PrefDelegate(sharedPreference, Keys.REFRESH_TOKEN, "")
    var isTokenExpired: Boolean? by PrefDelegate(sharedPreference, Keys.IS_TOKEN_EXPIRED, false)
    var isLoggedIn: Boolean? by PrefDelegate(sharedPreference, Keys.LOGIN_STATUS, false)
    var theme: Boolean by PrefDelegate(sharedPreference, Keys.THEME, false)
    var msisdn: String? by PrefDelegate(sharedPreference, Keys.MSISDN, "")


    object Keys {
        const val THEME = "THEME"
        const val LOGIN_STATUS = "LOGIN_STATUS"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val IS_TOKEN_EXPIRED = "IS_TOKEN_EXPIRED"
        const val MSISDN = "MSISDN"
    }
}