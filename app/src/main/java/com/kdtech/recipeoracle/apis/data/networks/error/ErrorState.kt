package com.kdtech.recipeoracle.apis.data.networks.error

import java.net.UnknownHostException

sealed class ErrorState : Throwable() {
    object NoInternetException : ErrorState()
    object Undefined : ErrorState()
    data class CallError(val error: Throwable) : ErrorState()
    data class GenericApiError(val error: APIError) : ErrorState()
    object ServerDownError : ErrorState()
}

fun Throwable.getErrorMessage(): String? = when (this) {
    is ErrorState.GenericApiError -> error.detail?.get(0)?.msg
    else -> message
}

fun Throwable?.isUnknownHostException(): Boolean = this is UnknownHostException
