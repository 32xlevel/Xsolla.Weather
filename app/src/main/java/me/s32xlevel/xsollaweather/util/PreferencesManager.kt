package me.s32xlevel.xsollaweather.util

import android.content.Context

object PreferencesManager {
    const val LAST_NETWORK_CONNECT = "LastNetworkConnect"
    const val SAVED_CITY = "SavedCity"
    const val EMPTY_VALUE = -1

    fun Context.setToPreferences(key: String, value: Long) {
        getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putLong(key, value).apply()
    }

    fun Context.getLongFromPreferences(key: String): Long {
        return getSharedPreferences("prefs", Context.MODE_PRIVATE).getLong(key, -1)
    }

    fun Context.setToPreferences(key: String, value: Int) {
        getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putInt(key, value).apply()
    }

    fun Context.getIntFromPreferences(key: String): Int {
        return getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt(key, -1)
    }
}