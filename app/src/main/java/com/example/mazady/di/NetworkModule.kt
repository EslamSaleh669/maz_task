package com.example.mazady.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.mazady.data.network.ApiClient
import com.example.mazady.util.Constants

import com.example.mazady.util.ErrorInterceptor

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


@Module
class NetworkModule(val context: Context) {


    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .cache(cache())
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(ErrorInterceptor())
            .addNetworkInterceptor(networkInterceptor())
            .addInterceptor(offlineInterceptor())
            .build()

    @Provides
    fun provideApiClient(client: OkHttpClient): ApiClient =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build().create(ApiClient::class.java)

    private fun cache(): Cache {
        return Cache(
            File(context.cacheDir, "someIdentifier"), Constants.CASHSIZE
        )

    }

    private fun offlineInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {


                var request: Request = chain.request()

                if (checkInternetConnectivity()) {
                    val cacheControl: CacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(Constants.HEADER_PRAGMA)
                        .removeHeader(Constants.HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                return chain.proceed(request)
            }
        }
    }


    private fun networkInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {

                val response: Response = chain.proceed(chain.request())
                val cacheControl: CacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()
                return response.newBuilder()
                    .removeHeader(Constants.HEADER_PRAGMA)
                    .removeHeader(Constants.HEADER_CACHE_CONTROL)
                    .header(
                        Constants.HEADER_CACHE_CONTROL, cacheControl.toString()
                    )
                    .build()
            }
        }
    }

    fun checkInternetConnectivity(): Boolean {
        val manager: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return manager?.activeNetworkInfo?.isConnected ?: false
    }

}