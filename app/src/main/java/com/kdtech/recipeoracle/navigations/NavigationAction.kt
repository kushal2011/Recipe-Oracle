package com.kdtech.recipeoracle.navigations

import android.os.Parcelable
import androidx.navigation.NavOptions

interface NavigationAction {
    val screen: Screen
    val stringArguments: Map<String, String>
        get() = emptyMap()
    val parcelableArguments: Map<String, Parcelable>
        get() = emptyMap()
    val navOptions: NavOptions
        get() = NavOptions.Builder().build() // No NavOptions as default
}

object ScreenAction {
    fun goTo(
        screen: Screen,
        map: Map<String, String> = emptyMap(),
        parcelableMap: Map<String, Parcelable> = emptyMap(),
        canLaunchSingleTop: Boolean = true
    ) = object : NavigationAction {
        override val screen: Screen
            get() = screen
        override val stringArguments: Map<String, String>
            get() = map
        override val parcelableArguments: Map<String, Parcelable>
            get() = parcelableMap
        override val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(canLaunchSingleTop)
            .build()
    }

    fun goToByClearStack(
        screen: Screen,
        map: Map<String, String> = emptyMap(),
        parcelableMap: Map<String, Parcelable> = emptyMap()
    ) = object : NavigationAction {
        override val screen: Screen
            get() = screen
        override val stringArguments: Map<String, String>
            get() = map
        override val parcelableArguments: Map<String, Parcelable>
            get() = parcelableMap
        override val navOptions = NavOptions.Builder()
            .setPopUpTo(0, true)
            .setLaunchSingleTop(true)
            .build()
    }

    fun clearStackUpToAndGoToScreen(
        clearStackUpToScreen: Screen,
        destScreen: Screen,
        map: Map<String, String> = emptyMap(),
        parcelableMap: Map<String, Parcelable> = emptyMap(),
        inclusive: Boolean = false
    ) = object : NavigationAction {
        override val screen: Screen
            get() = destScreen
        override val stringArguments: Map<String, String>
            get() = map
        override val parcelableArguments: Map<String, Parcelable>
            get() = parcelableMap
        override val navOptions = NavOptions.Builder()
            .setPopUpTo(clearStackUpToScreen.route, inclusive)
            .setLaunchSingleTop(true)
            .build()
    }
}
