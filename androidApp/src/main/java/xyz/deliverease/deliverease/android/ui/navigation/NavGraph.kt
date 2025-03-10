package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.deliverease.deliverease.android.ui.screens.AddDeliveryRecipientsScreen
import xyz.deliverease.deliverease.android.ui.screens.AddDeliveryScreen
import xyz.deliverease.deliverease.android.ui.screens.HomeScreen
import xyz.deliverease.deliverease.android.ui.screens.LoginScreen
import xyz.deliverease.deliverease.android.ui.screens.ProfileScreen
import xyz.deliverease.deliverease.android.ui.screens.RegisterScreen

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
            LoginScreen()
        }
        composable(NavDestination.Register.route) {
            RegisterScreen()
        }
        composable(NavDestination.AddDelivery.route) {
            AddDeliveryScreen()
        }
        composable(NavDestination.AddDeliveryRecipients.route) {
            AddDeliveryRecipientsScreen()
        }
        composable(NavDestination.Account.route) {
            ProfileScreen()
        }
    }
}