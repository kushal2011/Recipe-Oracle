package com.kodedynamic.recipeoracle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.kodedynamic.recipeoracle.common.Empty
import com.kodedynamic.recipeoracle.navigations.AppNavigation
import com.kodedynamic.recipeoracle.navigations.BottomBar
import com.kodedynamic.recipeoracle.navigations.PrimaryNavigator
import com.kodedynamic.recipeoracle.navigations.ScreenNavigator
import com.kodedynamic.recipeoracle.resources.StringResources
import com.kodedynamic.recipeoracle.resources.compositions.DefaultConfirmDialog
import com.kodedynamic.recipeoracle.resources.theme.RecipeTheme
import com.scottyab.rootbeer.RootBeer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootBeer = RootBeer(this)

        enableEdgeToEdge()
        setContent {
            RecipeTheme{
                if (rootBeer.isRooted && BuildConfig.DEBUG.not()) {
                    DefaultConfirmDialog(
                        title = stringResource(id = StringResources.rootedDeviceTitle),
                        message = stringResource(id = StringResources.rootedDeviceDesc),
                        dismissOnClickOutside = false,
                        onRightClick = { finish() },
                        leftText = String.Empty
                    )
                } else {
                    val navController = rememberNavController()
                    val recipeId = getRecipeIdIfDeepLink()
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController)
                        },
                        content = {
                            Surface(
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(it)
                            ) {
                                PrimaryNavigator(
                                    screenNavigator = screenNavigator,
                                    navController = navController,
                                    lifecycleOwner = this
                                )
                                AppNavigation(
                                    navController,
                                    recipeId
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    private fun getRecipeIdIfDeepLink(): String {
        // Check if the intent contains a deep link
        intent?.data?.let { uri ->
            // Extract the recipeId from the path segments
            Log.e("aaa", "intent data: $uri", )
            Log.e("aaa", "intent pathSegments: ${uri.pathSegments}")
            val pathSegments = uri.pathSegments
            if (pathSegments.size > 1 && pathSegments[0] == "recipe") {
                val recipeId = pathSegments[1] // Extract the recipe ID from the deep link
                return recipeId
            } else {
                return String.Empty
            }
        } ?: return String.Empty
    }
}

