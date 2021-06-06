package com.gem.mediaplayers.data.network

import android.content.Context
import android.os.Build
import android.util.Log
import com.gem.mediaplayers.data.network.header.ApiHeader
import com.gem.mediaplayers.BuildConfig
import com.gem.mediaplayers.R
import com.gem.mediaplayers.data.local.PreferencesHelper
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(
    var context: Context,
    private var apiHeader: ApiHeader,
    private val preferencesHelper: PreferencesHelper
) : ApiHelper {

    private var retrofit: Retrofit
    private var apiService: ApiService

    companion object {
        const val REQUEST_TIMEOUT = 25L
        const val API_ENDPOINT = "kk-api/"
    }

    init {
        val okHttpClient = initOkHttp()

        retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url) + API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    private fun initOkHttp(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 100
        dispatcher.maxRequestsPerHost = 6
        val httpClient = OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            httpClient.interceptors().add(logging)
        }

        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            //TODO
            val url = original.url()
                .newBuilder()
//                .addQueryParameter(LANG, getAppLanguage())
                .build()

            val token = apiHeader.authHeader.token
            val requestBuilder = original.newBuilder()
                .header("User-Agent", createOriginalUserAgent())
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("timeZone", TimeZone.getDefault().id.toString())

            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            if (BuildConfig.DEBUG) {
                Log.d("AppApiHelper", "Authorization Bearer $token")
            }

            val request = requestBuilder.method(original.method(), original.body())
                .url(url)
                .build()
            chain.proceed(request)
        }

        return httpClient.build()
    }

    private fun createOriginalUserAgent(): String {
        return "KICKENGLISH/${BuildConfig.VERSION_NAME} (${Build.MODEL})"
    }

    override fun getAppHeader(): ApiHeader {
        return apiHeader
    }
}
