package me.s32xlevel.xsollaweather.business.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.gson.GsonBuilder
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.util.LockManager
import me.s32xlevel.xsollaweather.util.PreferencesManager
import me.s32xlevel.xsollaweather.util.PreferencesManager.setToPreferences
import me.s32xlevel.xsollaweather.util.ToastManager.showToast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "3b19c25dbd0a3120f572221bc07f83cc"
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

val api: WeatherApi by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(6, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(WeatherApi::class.java)
}

fun <T> Fragment.asyncCall(
    onSuccess: (response: Response<T>) -> Unit,
    onFailure: () -> Unit,
    lockCallback: ((Boolean) -> Unit)? = LockManager::changeLockState
) = CallbackImpl(this, onSuccess, onFailure, lockCallback)

class CallbackImpl<T>(
    private val fragment: Fragment,
    private val onSuccess: (response: Response<T>) -> Unit,
    private val onFailure: () -> Unit,
    private val lockCallback: ((Boolean) -> Unit)? = LockManager::changeLockState
) : Callback<T> {

    init {
        lockCallback?.invoke(true)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        lockCallback?.invoke(false)

        when (response.code()) {
            200 -> {
                fragment.context?.setToPreferences(PreferencesManager.LAST_NETWORK_CONNECT, System.currentTimeMillis())
                onSuccess.invoke(response)
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        lockCallback?.invoke(false)
        fragment.showToast(R.string.network_error)
        onFailure.invoke()
    }
}

fun FragmentActivity.checkInternetConnection(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworks.isNotEmpty()
}