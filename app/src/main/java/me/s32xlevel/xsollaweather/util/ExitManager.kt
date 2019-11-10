package me.s32xlevel.xsollaweather.util

import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import me.s32xlevel.xsollaweather.R

object ExitManager {
    private const val TIME_INTER_TWO_INITIATIVES_TO_EXIT = 1000L
    private var lastExitInitiative = 0L

    fun FragmentActivity.onExitInitiative() {
        val now = System.currentTimeMillis()
        val time = now - lastExitInitiative
        lastExitInitiative = now

        val fragmentView =
            supportFragmentManager.findFragmentByTag(NavigationManager.FRAGMENT_TAG)?.view ?: return

        if (time > TIME_INTER_TWO_INITIATIVES_TO_EXIT) {
            Snackbar.make(
                fragmentView,
                getString(R.string.snackbar_exit_text),
                (TIME_INTER_TWO_INITIATIVES_TO_EXIT / 1.5).toInt()
            )
                .show()
        } else {
            finish()
        }
    }
}