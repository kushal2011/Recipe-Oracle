package com.kdtech.recipeoracle.navigations

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kdtech.recipeoracle.resources.DrawableResources
import com.kdtech.recipeoracle.resources.StringResources
import com.kdtech.recipeoracle.resources.theme.RecipeTheme

@Composable
fun BottomBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem(
            route = Screen.Home().route,
            text = stringResource(StringResources.home),
            icon = DrawableResources.homeIcon
        ),
        BottomNavItem(
            route = Screen.Categories().route,
            text = stringResource(StringResources.categories),
            icon = DrawableResources.categoriesIcon
        ),
        BottomNavItem(
            route = Screen.Search().route,
            text = stringResource(StringResources.search),
            icon = DrawableResources.searchIcon
        ),
        BottomNavItem(
            route = Screen.RecipeChat().route,
            text = stringResource(StringResources.chat),
            icon = DrawableResources.chatIcon
        )
    )

    if (currentRoute !in items.map { it.route }) {
        return
    }
    NavigationBar(
        containerColor = RecipeTheme.colors.veryLightGray,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(brush = Brush.horizontalGradient(listOf(Color.Gray, Color.White)))
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            val scale by animateFloatAsState(if (isSelected) 1.1f else 1.0f)
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.scale(scale),
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.route,
                        tint = if (isSelected) {
                            RecipeTheme.colors.primaryGreen
                        } else {
                            RecipeTheme.colors.darkCharcoal
                        }
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = screen.text,
                        color = if (isSelected) {
                            RecipeTheme.colors.primaryGreen
                        } else {
                            RecipeTheme.colors.darkCharcoal
                        },
                        fontWeight = if (isSelected) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = RecipeTheme.colors.primaryGreen,
                    unselectedIconColor = RecipeTheme.colors.darkCharcoal,
                    selectedTextColor = RecipeTheme.colors.primaryGreen,
                    unselectedTextColor = RecipeTheme.colors.darkCharcoal,
                    indicatorColor = RecipeTheme.colors.lightGreen
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val text: String,
    @DrawableRes val icon: Int
)