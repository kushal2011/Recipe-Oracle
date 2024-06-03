package com.kdtech.recipeoracle.navigations

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kdtech.recipeoracle.features.detailsscreen.ui.DetailsScreen
import com.kdtech.recipeoracle.features.homescreen.ui.HomeScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screen.Home().route) {
        composable(Screen.Home().route) {
            HomeScreen(
                viewModel = hiltViewModel()
            )
        }
        composable(Screen.Details().route) {
            DetailsScreen()
        }
    }
}

@Composable
fun PrimaryNavigator(
    screenNavigator: ScreenNavigator,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner
) {
    val currentRoute = navController.currentDestination?.route
    val navigatorState by screenNavigator.navActions
        .asLifecycleAwareState(
            lifecycleOwner = lifecycleOwner,
            initialState = null
        )
    LaunchedEffect(navigatorState) {
        navigatorState?.let { navigationAction ->
            navigationAction.stringArguments.forEach { arg ->
                navController.currentBackStackEntry?.arguments?.putString(arg.key, arg.value)
            }
            navigationAction.parcelableArguments.forEach { arg ->
                navController.currentBackStackEntry?.arguments?.putParcelable(arg.key, arg.value)
            }
            when (navigationAction.screen) {
                is Screen.None -> return@LaunchedEffect
                is Screen.Back -> {
                    navController.navigateUp()
                }

                is Screen.Close -> navController.clearBackStack(0)
                else -> {
                    if (currentRoute != navigationAction.screen.route) {
                        if (navigationAction.stringArguments.isEmpty()) {
                            navController.navigate(
                                navigationAction.screen.route,
                                navigationAction.navOptions
                            )
                        } else {
                            navController.navigate(
                                getOptionalStringArgs(
                                    navigationAction.screen.route,
                                    navigationAction.stringArguments
                                ),
                                navigationAction.navOptions
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getOptionalStringArgs(route: String, stringArguments: Map<String, String>): String {
    val uri = Uri.parse(route).buildUpon()
    stringArguments.forEach {
        uri.appendQueryParameter(it.key, it.value)
    }
    return uri.toString()
}

@Composable
fun <T> Flow<T>.asLifecycleAwareState(lifecycleOwner: LifecycleOwner, initialState: T) =
    lifecycleAwareState(lifecycleOwner, this, initialState)

@Composable
fun <T> lifecycleAwareState(
    lifecycleOwner: LifecycleOwner,
    flow: Flow<T>,
    initialState: T
): State<T> {
    val lifecycleAwareStateFlow = remember(flow, lifecycleOwner) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    return lifecycleAwareStateFlow.collectAsState(initialState)
}