package com.makhalibagas.mygithubuser.data.remote.api

import android.content.Context
import android.content.Intent
import com.makhalibagas.mygithubuser.BuildConfig
import com.makhalibagas.mygithubuser.ui.main.view.MainActivity
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpOkClient(authInterceptor: AuthInterceptor, context: Context): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.apply {
        writeTimeout(60, TimeUnit.MINUTES)
        readTimeout(60, TimeUnit.MINUTES)
        callTimeout(60, TimeUnit.MINUTES)
        addInterceptor(authInterceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(ChuckInterceptor(context))
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }

    }

    return okHttpClient.build()
}

fun provideApiService(okHttpClient: OkHttpClient): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}

class AuthInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .addHeader("Authorization", "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")
            .build()
        val response = chain.proceed(request)

        if (response.code != 200) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("code", response.code)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

        return response
    }
}