package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val icon: ImageVector, val label: String) {
    data object Home : NavDestination("home", Icons.Outlined.Home, "Home")
    data object Login : NavDestination("login", Icons.AutoMirrored.Outlined.Login, "Login")
    data object Register : NavDestination("register", Icons.Outlined.PersonAdd, "Register")
    data object AddDelivery : NavDestination("add-delivery", Icons.Outlined.AddCircle, "Add Delivery")
    data object AddDeliveryRecipients : NavDestination("add-delivery-recipients", Icons.Outlined.PersonAdd, "Add Delivery")
    data object Account : NavDestination("account", Icons.Outlined.AccountCircle, "Account")
}

val bottomNavItems = listOf(
    NavDestination.Home,
    NavDestination.Login,
    NavDestination.AddDelivery,
    NavDestination.Account,
)