package com.kodedynamic.recipeoracle.apis.data.networks

import com.kodedynamic.recipeoracle.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class PrivateKeyInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestWithUserAgent = if (request.url.toString() == "https://api.openai.com/v1/chat/completions") {
            request.newBuilder().header(AUTH, "Bearer ${BuildConfig.OPENAI_API_KEY}").build()
        } else {
            request.newBuilder().header(SECRET_KEY, BuildConfig.SECRET_KEY).build()
        }

        return chain.proceed(requestWithUserAgent)
    }

    companion object {
        private const val SECRET_KEY = "X-API-Key"
        private const val AUTH = "Authorization"
    }
}
