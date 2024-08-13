package com.kdtech.recipeoracle.apis.data.networks

import com.kdtech.recipeoracle.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class PrivateKeyInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithUserAgent = request.newBuilder().header(SECRET_KEY, BuildConfig.SECRET_KEY).build()

        return chain.proceed(requestWithUserAgent)
    }

    companion object {
        private const val SECRET_KEY = "X-API-Key"
    }
}
