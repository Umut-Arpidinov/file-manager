package kg.o.internlabs.core.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

abstract class BasePrefs(private val context: Context) {

    abstract val prefFileName: String

    val sharedPreference: SharedPreferences by lazy {
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

}