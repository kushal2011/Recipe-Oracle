/*
 * NavigationModule.kt
 * Module: COTO.common.layers.presentation.di
 * Project: COTO
 * Copyright © 2022, Eve World Platform PTE LTD. All rights reserved.
 */

package com.kdtech.recipeoracle.navigations.di

import com.kdtech.recipeoracle.navigations.CustomScreenNavigator
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun providesEvaNavigator(): ScreenNavigator = CustomScreenNavigator()
}
