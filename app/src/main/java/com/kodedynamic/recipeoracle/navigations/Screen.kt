package com.kodedynamic.recipeoracle.navigations

sealed class Screen(open val route: String) : SealedState {
    data class None(override val route: String = "") : Screen(route)
    data class Home(override val route: String = "home") : Screen(route)
    data class Details(override val route: String = "details") : Screen(route)
    data class RecipeChat(override val route: String = "recipe_chat") : Screen(route)
    data class Categories(override val route: String = "categories") : Screen(route)
    data class SeeAllRecipes(override val route: String = "see_all_recipes") : Screen(route)
    data class Search(override val route: String = "Search") : Screen(route)
    data class ForceUpdate(override val route: String = "force_update") : Screen(route)
    data class Auth(override val route: String = "auth") : Screen(route)
    data class Back(override val route: String = "back") : Screen(route)
    data class Close(override val route: String = "close") : Screen(route)
}

interface SealedState