package com.kdtech.recipeoracle

import android.os.Bundle
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
import com.kdtech.recipeoracle.common.Empty
import com.kdtech.recipeoracle.navigations.AppNavigation
import com.kdtech.recipeoracle.navigations.BottomBar
import com.kdtech.recipeoracle.navigations.PrimaryNavigator
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import com.kdtech.recipeoracle.resources.StringResources
import com.kdtech.recipeoracle.resources.compositions.DefaultConfirmDialog
import com.kdtech.recipeoracle.resources.theme.RecipeTheme
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
                                AppNavigation(navController)
                            }
                        },
                    )
                }
            }
        }
    }
}
