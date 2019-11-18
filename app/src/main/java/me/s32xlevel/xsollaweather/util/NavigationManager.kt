package me.s32xlevel.xsollaweather.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import me.s32xlevel.xsollaweather.R

object NavigationManager {
    private const val FRAGMENT_TAG = "FragmentTag"

    fun FragmentActivity.changeFragment(fragment: Fragment, cleanStack: Boolean = false) {
        if (cleanStack) {
            clearBackStack()
        }

        hideKeyboard()

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
            addToBackStack(null)
            commit()
        }

    }

    private fun FragmentActivity.hideKeyboard() {
        val content = findViewById<View>(android.R.id.content)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(content.windowToken, 0)
    }

    private fun FragmentActivity.clearBackStack() {
        with(supportFragmentManager) {
            if (backStackEntryCount > 0) {
                val first = getBackStackEntryAt(0)
                popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }
}