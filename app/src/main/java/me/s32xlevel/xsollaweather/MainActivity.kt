package me.s32xlevel.xsollaweather

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.s32xlevel.xsollaweather.presentation.citychoose.CityChooseFragment
import me.s32xlevel.xsollaweather.presentation.citydetail.CityDetailFragment
import me.s32xlevel.xsollaweather.util.LockCallback
import me.s32xlevel.xsollaweather.util.LockManager
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.getIntFromPreferences

class MainActivity : AppCompatActivity(), LockCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({ xsolla_screen.animate().setDuration(400).alpha(0f) }, 2000)

        if (getIntFromPreferences(PreferencesManager.SAVED_CITY) == -1) {
            changeFragment(CityChooseFragment.newInstance())
        } else {
            changeFragment(CityDetailFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        LockManager.lockCallback = this
    }

    override fun onPause() {
        LockManager.lockCallback = null
        super.onPause()
    }

    @UiThread
    override fun lock() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progress_bar.visibility = View.VISIBLE
        transparent_background.alpha = 0.5f
    }


    @UiThread
    override fun unlock() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progress_bar.visibility = View.GONE
        transparent_background.alpha = 0f
    }
}
