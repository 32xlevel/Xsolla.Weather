package me.s32xlevel.xsollaweather

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.s32xlevel.xsollaweather.ui.fragment.CityChooseFragment
import me.s32xlevel.xsollaweather.util.ExitManager.onExitInitiative
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if ((getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworks.isNotEmpty()) {
            App.getInstance().getDatabase().weatherRepository().clear()
        }

        // TODO (Если город ранее не был выбран, то выбор города)
        changeFragment(CityChooseFragment.newInstance())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            onExitInitiative()
        }
    }
}
