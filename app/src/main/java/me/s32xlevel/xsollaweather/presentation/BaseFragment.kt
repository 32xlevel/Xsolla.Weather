package me.s32xlevel.xsollaweather.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import me.s32xlevel.xsollaweather.App
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    val cityRepository by lazy { App.getInstance().getDatabase().cityRepository() }

    val weatherRepository by lazy { App.getInstance().getDatabase().weatherRepository() }

    fun changeFragment(fragment: Fragment, cleanStack: Boolean = false) =
        activity?.changeFragment(fragment, cleanStack)
}