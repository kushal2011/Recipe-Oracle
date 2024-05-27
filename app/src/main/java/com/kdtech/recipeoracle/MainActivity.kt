package com.kdtech.recipeoracle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.GenerativeModel
import com.kdtech.recipeoracle.navigations.AppNavigation
import com.kdtech.recipeoracle.navigations.PrimaryNavigator
import com.kdtech.recipeoracle.navigations.ScreenNavigator
import com.kdtech.recipeoracle.ui.theme.RecipeOracleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var textData by remember { mutableStateOf("") }
            val navController = rememberNavController()

            LaunchedEffect(key1 = Unit) {
                textData = getData()
            }
            Scaffold(
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

    private suspend fun getData() : String{
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMENI_API_KEY
        )
        val prompt = "Give me a recipie for Dabeli."
        val response = generativeModel.generateContent(prompt)
        return response.text.orEmpty()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeOracleTheme {
        Greeting("Android")
    }
}