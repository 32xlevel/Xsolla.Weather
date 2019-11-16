package me.s32xlevel.xsollaweather.util

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_banner.*
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.util.ToastManager.showToast

object ErrorBannerManager {
    fun Fragment.showErrorBanner(onClickListener: () -> Unit) {
        showBanner()
        with (activity!!) {
            error_button.setOnClickListener {
                if (checkInternetConnection()) {
                    hideBanner()
                    onClickListener.invoke()
                } else {
                    showToast(R.string.network_error)
                }
            }
        }
    }

    private fun Fragment.hideBanner() {
        with(activity!!) {
            error_banner.animate().setDuration(400).alpha(0f)
            error_banner.visibility = View.GONE
            (this as AppCompatActivity).supportActionBar?.show()
        }
    }

    private fun Fragment.showBanner() {
        with(activity!!) {
            error_banner.visibility = View.VISIBLE
            error_banner.animate().setDuration(400).alpha(1f)
            (this as AppCompatActivity).supportActionBar?.hide()
        }
    }

    private fun FragmentActivity.checkInternetConnection(): Boolean {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworks.isNotEmpty()
    }
}