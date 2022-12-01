package kg.o.internlabs.core.data.local.prefs

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

private fun <T> StoragePreferences.getFlow(key: String, value: () -> T) = callbackFlow {
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        if (k == null || k == key) trySend(value())
    }

    sharedPreference.registerOnSharedPreferenceChangeListener(listener)
    trySend(value())
    awaitClose { sharedPreference.unregisterOnSharedPreferenceChangeListener(listener) }
}.conflate()

fun StoragePreferences.getBooleanFlow(key: String, defValue: Boolean) =
    getFlow(key) { sharedPreference.getBoolean(key, defValue) }

fun StoragePreferences.getStringFlow(key: String, defValue: String?) =
    getFlow(key) { sharedPreference.getString(key, defValue) }

fun StoragePreferences.getStringSetFlow(key: String, defValue: Set<String>?) =
    getFlow(key) { sharedPreference.getStringSet(key, defValue) }

fun SharedPreferences.put(key: String, value: Any?) {
    edit().apply {
        when (value) {
            is String -> putString(key, value.toString())
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
        }
    }.apply()
}
