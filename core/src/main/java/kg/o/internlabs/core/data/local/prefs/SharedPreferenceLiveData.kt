package kg.o.internlabs.core.data.local.prefs

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

private class SharedPreferenceLiveData<T>(
    private val storagePrefs: StoragePreferences,
    private val key: String,
    private val getPreferenceValue: () -> T,
) : LiveData<T>(getPreferenceValue()), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onActive() {
        storagePrefs.sharedPreference.registerOnSharedPreferenceChangeListener(this)
        updateIfChanged()
    }

    override fun onInactive() =
        storagePrefs.sharedPreference.unregisterOnSharedPreferenceChangeListener(this)

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == this.key || key == null) {
            // Note that we get here on every preference write, even if the value has not changed
            updateIfChanged()
        }
    }

    /** Update the live data value, but only if the value has changed. */
    private fun updateIfChanged() = with(getPreferenceValue()) { if (value != this) value = this }
}

fun StoragePreferences.liveData(key: String, default: Boolean): LiveData<Boolean> =
    SharedPreferenceLiveData(this, key) { sharedPreference.getBoolean(key, default) }

fun StoragePreferences.liveData(key: String, default: String?): LiveData<String?> =
    SharedPreferenceLiveData(this, key) { sharedPreference.getString(key, default) }

fun StoragePreferences.liveData(key: String, default: Set<String>?): LiveData<Set<String>?> =
    SharedPreferenceLiveData(this, key) { sharedPreference.getStringSet(key, default) }