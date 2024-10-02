package com.invictus.bloghub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.invictus.bloghub.screens.AddBlog
import com.invictus.bloghub.screens.FullBlogScreen
import com.invictus.bloghub.screens.LoginScreen
import com.invictus.bloghub.screens.MainScreen
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            window.statusBarColor = android.graphics.Color.BLACK
               App()
            }
        }
}


@Composable
fun App() {
    val navcontroller=rememberNavController()
    NavHost(navController = navcontroller, startDestination = LoginScreen) {

        composable<LoginScreen>{
        LoginScreen(navcontroller)
        }

        composable<MainScreen>{
            MainScreen(navcontroller)
        }

        composable<AddBlogScreen>{
            AddBlog(navcontroller)
        }
        composable<FullBlogScreen> {
         val args=it.toRoute<FullBlogScreen>()
         FullBlogScreen(args)
        }

    }
}

@Serializable
object LoginScreen

@Serializable
object AddBlogScreen

@Serializable
data class MainScreen(
val name :String
)

@Serializable
data class FullBlogScreen(
    val blog: String?,
    val blogdesc: String?,
    val blogcategory: String?
)



