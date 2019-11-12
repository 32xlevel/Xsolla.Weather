package me.s32xlevel.xsollaweather.network

import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import me.s32xlevel.xsollaweather.R
import me.s32xlevel.xsollaweather.util.ToastManager.showToast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "3b19c25dbd0a3120f572221bc07f83cc"
const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

val api: WeatherApi by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(WeatherApi::class.java)
}

fun <T> Fragment.asyncCall(
    onSuccess: (response: Response<T>) -> Unit,
    onBadRequest: () -> Unit
) = CallbackImpl(this, onSuccess, onBadRequest)

class CallbackImpl<T>(
    private val fragment: Fragment,
    private val onSuccess: (response: Response<T>) -> Unit,
    private val onBadRequest: () -> Unit
) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when (response.code()) {
            200 -> onSuccess.invoke(response)
            else -> onBadRequest.invoke()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        fragment.showToast(R.string.network_error)
    }
}