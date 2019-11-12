package me.s32xlevel.xsollaweather.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

@Suppress("unused")
object ToastManager {
    private var toast: Toast? = null

    fun Fragment.showToast(@StringRes stringRes : Int) {
        context?.showToast(stringRes)
    }

    fun Fragment.showToast(string: String) {
        context?.showToast(string)
    }

    fun Context.showToast(string: String) {
        toast?.cancel()
        toast = Toast.makeText(this, string, Toast.LENGTH_LONG)
        toast?.show()
    }

    fun Context.showToast(@StringRes stringRes : Int) {
        toast?.cancel()
        toast = Toast.makeText(this, getString(stringRes), Toast.LENGTH_LONG)
        toast?.show()
    }
}