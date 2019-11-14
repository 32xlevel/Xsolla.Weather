package me.s32xlevel.xsollaweather

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import me.s32xlevel.xsollaweather.ui.fragment.CityChooseFragment
import me.s32xlevel.xsollaweather.ui.fragment.CityDetailFragment
import me.s32xlevel.xsollaweather.util.ExitManager.onExitInitiative
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.getIntFromPreferences
import me.s32xlevel.xsollaweather.util.PreferencesManager.getLongFromPreferences
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

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
            onExitInitiative()
        }
    }
}
