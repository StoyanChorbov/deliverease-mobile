package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.deliverease.deliverease.android.ui.screens.AddDeliveryScreen
import xyz.deliverease.deliverease.android.ui.screens.HomeScreen
import xyz.deliverease.deliverease.android.ui.screens.LoginScreen
import xyz.deliverease.deliverease.android.ui.screens.ProfileScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavDestination.Home.route
    ) {
        composable(NavDestination.Home.route) {
            HomeScreen()
        }
        composable(NavDestination.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(NavDestination.AddDelivery.route) {
            AddDeliveryScreen()
        }
        composable(NavDestination.Account.route) {
            ProfileScreen()
        }
    }
}