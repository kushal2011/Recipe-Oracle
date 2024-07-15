package com.kdtech.recipeoracle.apis.domain

import kotlinx.coroutines.flow.Flow

sealed interface UseCase {
    interface Suspending<R> : UseCase {
        suspend operator fun invoke(): R
    }

    interface SuspendingParameterized<T, R> : UseCase {
        suspend operator fun invoke(param: T): R
    }

    interface AsyncStream<R> : UseCase {
        operator fun invoke(): Flow<R>
    }

    interface AsyncStreamParameterized<T, R> : UseCase {
        operator fun invoke(param: T): Flow<R>
    }
}
