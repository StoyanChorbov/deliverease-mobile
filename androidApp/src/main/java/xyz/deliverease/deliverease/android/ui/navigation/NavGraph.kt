package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.deliverease.deliverease.android.ui.screens.AddDeliveryRecipientsScreen
import xyz.deliverease.deliverease.android.ui.screens.AddDeliveryRecipientsScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.AddDeliveryScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.DeliveryDetailsScreen
import xyz.deliverease.deliverease.android.ui.screens.HomeScreen
import xyz.deliverease.deliverease.android.ui.screens.HomeScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.LoginScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.ProfileScreen
import xyz.deliverease.deliverease.android.ui.screens.RegisterScreenRoot

// The NavGraph to map all routes/endpoint to their corresponding screens/composables
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: NavDestination = NavDestination.Home,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(NavDestination.Home.route) {
            HomeScreenRoot()
        }
        composable("${NavDestination.DeliveryDetails.route}/{deliveryId}") {
            val deliveryId = it.arguments?.getString("deliveryId")
            if (deliveryId == null)
                HomeScreenRoot()
            else
                DeliveryDetailsScreen(deliveryId = deliveryId)
        }
        composable(NavDestination.Login.route) {
            LoginScreenRoot()
        }
        composable(NavDestination.Register.route) {
            RegisterScreenRoot()
        }
        composable(NavDestination.AddDelivery.route) {
            AddDeliveryScreenRoot()
        }
        composable(NavDestination.AddDeliveryRecipients.route) {
            AddDeliveryRecipientsScreenRoot()
        }
        composable(NavDestination.Account.route) {
            ProfileScreen()
        }
    }
}