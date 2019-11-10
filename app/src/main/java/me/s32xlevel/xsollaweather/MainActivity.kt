package me.s32xlevel.xsollaweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.s32xlevel.xsollaweather.ui.CityChooseFragment
import me.s32xlevel.xsollaweather.util.ExitManager.onExitInitiative
import me.s32xlevel.xsollaweather.util.NavigationManager.changeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
