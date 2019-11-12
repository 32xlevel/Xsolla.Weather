package me.s32xlevel.xsollaweather.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import me.s32xlevel.xsollaweather.R

object NavigationManager {
    const val FRAGMENT_TAG = "FragmentTag"

    // TODO: Добавить город, удалить из стэка этот фрагмент
    fun FragmentActivity.changeFragment(fragment: Fragment, cleanStack: Boolean = false, addToBackStack: Boolean = true) {
        if (cleanStack) {
            clearBackStack()
        }

        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
            if (addToBackStack) addToBackStack(null)
            commit()
        }

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