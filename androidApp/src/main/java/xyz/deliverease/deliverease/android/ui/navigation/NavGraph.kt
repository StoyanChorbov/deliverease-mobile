package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.deliverease.deliverease.android.ui.screens.delivery.AddDeliveryScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.delivery.DeliveryDetailsScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.delivery.FindDeliveryScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.HomeScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.user.LoginScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.user.ProfileScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.user.RegisterScreenRoot
import xyz.deliverease.deliverease.android.ui.screens.delivery.PastDeliveriesScreenRoot

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
        composable(NavDestination.DeliveryDetails.route) {
            HomeScreenRoot()
        }
        composable("${NavDestination.DeliveryDetails.route}/{deliveryId}") {
            val deliveryId = it.arguments?.getString("deliveryId")
            if (deliveryId == null)
                HomeScreenRoot()
            else
                DeliveryDetailsScreenRoot(deliveryId = deliveryId)
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
        composable(NavDestination.FindDelivery.route) {
            FindDeliveryScreenRoot()
        }
//        composable(NavDestination.FindableDelivery.route) {
//            FindDeliveryScreenRoot()
//        }
//        composable("${NavDestination.FindDelivery.route}/{deliveryId}") {
//            val deliveryId = it.arguments?.getString("deliveryId")
//            if (deliveryId == null)
//                HomeScreenRoot()
//            else
//                FindableDeliveryScreenRoot(deliveryId = deliveryId)
//        }
        composable(NavDestination.PastDeliveries.route) {
            PastDeliveriesScreenRoot()
        }
        composable(NavDestination.Account.route) {
            ProfileScreenRoot()
        }
    }
}