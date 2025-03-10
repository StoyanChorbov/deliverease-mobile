package xyz.deliverease.deliverease.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import xyz.deliverease.deliverease.android.config.androidAppModule
import xyz.deliverease.deliverease.android.ui.navigation.NavBar
import xyz.deliverease.deliverease.android.ui.navigation.NavGraph
import xyz.deliverease.deliverease.android.ui.theme.DelivereaseTheme
import xyz.deliverease.deliverease.appModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule, androidAppModule)
        }
        setContent {
            DelivereaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

val LocalNavController =
    staticCompositionLocalOf<NavController> {
        error("No NavController provided")
    }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            bottomBar = { NavBar() }
        ) {
            NavGraph(navController = navController)
        }
    }
}

fun navigateTo(navController: NavController, route: String) =
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }