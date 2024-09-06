package com.kodedynamic.recipeoracle.navigations.di

import com.kodedynamic.recipeoracle.navigations.CustomScreenNavigator
import com.kodedynamic.recipeoracle.navigations.ScreenNavigator
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
