package me.s32xlevel.xsollaweather

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.annotation.UiThread
import kotlinx.android.synthetic.main.activity_main.*
import me.s32xlevel.xsollaweather.ui.fragment.CityChooseFragment
import me.s32xlevel.xsollaweather.ui.fragment.CityDetailFragment
import me.s32xlevel.xsollaweather.util.LockCallback
import me.s32xlevel.xsollaweather.util.LockManager
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.getIntFromPreferences
import me.s32xlevel.xsollaweather.util.PreferencesManager.getLongFromPreferences
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LockCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({ xsolla_screen.animate().setDuration(400).alpha(0f) }, 2000)

        // Если есть интернет-соединение и с момента последнего запроса данных прошло больше 2 часов,
        // то удаляем данные из базы
        if ((getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworks.isNotEmpty()) {
            val lastConnect = getLongFromPreferences(PreferencesManager.LAST_NETWORK_CONNECT)
            if (lastConnect != -1L && System.currentTimeMillis() - lastConnect > TimeUnit.HOURS.toMillis(2)) {
                App.getInstance().getDatabase().weatherRepository().clear()
            }
        }

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
