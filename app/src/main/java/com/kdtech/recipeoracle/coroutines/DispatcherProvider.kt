package com.kdtech.recipeoracle.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

@Retention(AnnotationRetention.RUNTIME)

@Qualifier
annotation class ApplicationDefaultScope

@Qualifier
annotation class ApplicationMainScope

@Qualifier
annotation class ApplicationIoScope