
package com.kdtech.recipeoracle.coroutines.di

import com.kdtech.recipeoracle.coroutines.ApplicationDefaultScope
import com.kdtech.recipeoracle.coroutines.ApplicationIoScope
import com.kdtech.recipeoracle.coroutines.ApplicationMainScope
import com.kdtech.recipeoracle.coroutines.DispatcherProvider
import com.kdtech.recipeoracle.coroutines.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    fun providesDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()

    @Singleton
    @ApplicationDefaultScope
    @Provides
    fun providesDefaultCoroutineScope(
        dispatcherProvider: DispatcherProvider
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcherProvider.default)

    @Singleton
    @ApplicationMainScope
    @Provides
    fun providesMainCoroutineScope(
        dispatcherProvider: DispatcherProvider
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcherProvider.main)

    @Singleton
    @ApplicationIoScope
    @Provides
    fun providesIoCoroutineScope(
        dispatcherProvider: DispatcherProvider
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcherProvider.io)
}
