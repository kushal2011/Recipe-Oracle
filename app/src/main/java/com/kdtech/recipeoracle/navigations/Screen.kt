package com.kdtech.recipeoracle.navigations

sealed class Screen(open val route: String) : SealedState {
    data class None(override val route: String = "") : Screen(route)
    data class Home(override val route: String = "home") : Screen(route)
    data class Details(override val route: String = "details") : Screen(route)
    data class Back(override val route: String = "back") : Screen(route)
    data class Close(override val route: String = "close") : Screen(route)
}

interface SealedState