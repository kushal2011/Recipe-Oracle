package com.kdtech.recipeoracle.apis.data.networks

import com.google.gson.Gson
import com.kdtech.recipeoracle.apis.data.networks.APIErrorConstants.ERROR_400_SERIES
import com.kdtech.recipeoracle.apis.data.networks.APIErrorConstants.ERROR_500_SERIES
import com.kdtech.recipeoracle.apis.data.networks.error.APIError
import com.kdtech.recipeoracle.apis.data.networks.error.ErrorState
import okhttp3.Headers
import retrofit2.Response
import java.io.IOException

suspend inline fun <reified T> safeApiCall(
    call: () -> Response<T>,
    errorHandler: (t: Throwable) -> Throwable,
    noinline responseHeaders: (suspend (Headers) -> Unit)? = null
): Result<T> {
    return try {
        val response: Response<T> = call()
        if (response.isSuccessful) {
            responseHeaders?.invoke(response.headers())
            Result.success(response.body()!!)
        } else {
            when (response.code()) {
                in ERROR_400_SERIES -> {
                    val apiError = Gson().fromJson(response.errorBody()?.charStream(), APIError::class.java)
                    Result.failure(ErrorState.GenericApiError(apiError))
                }

                in ERROR_500_SERIES -> Result.failure(ErrorState.ServerDownError)
                else -> Result.failure(ErrorState.Undefined)
            }
        }
    } catch (ioexception: IOException) {
        Result.failure(ErrorState.NoInternetException)
    } catch (exception: Exception) {
        Result.failure(ErrorState.CallError(errorHandler(exception)))
    }
}

object APIErrorConstants {
    private const val ERROR_400 = 400
    private const val ERROR_499 = 499
    private const val ERROR_500 = 500
    private const val ERROR_599 = 599

    val ERROR_400_SERIES = ERROR_400..ERROR_499
    val ERROR_500_SERIES = ERROR_500..ERROR_599
}
