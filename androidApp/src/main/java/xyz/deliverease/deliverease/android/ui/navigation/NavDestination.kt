package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val icon: ImageVector, val label: String) {
    // The details for all screens / statically-typed navigation
    data object Home : NavDestination("home", Icons.Outlined.Home, "Home")
    data object DeliveryDetails : NavDestination("delivery-details", Icons.Outlined.Home, "Delivery Details")
    data object FindDelivery : NavDestination("find-delivery", Icons.Outlined.Home, "Find Delivery")
    data object FindableDelivery : NavDestination("findable-delivery", Icons.Outlined.Home, "Findable Delivery")
    data object Login : NavDestination("login", Icons.AutoMirrored.Outlined.Login, "Login")
    data object Register : NavDestination("register", Icons.Outlined.PersonAdd, "Register")
    data object AddDelivery : NavDestination("add-delivery", Icons.Outlined.AddCircle, "Add Delivery")
    data object AddDeliveryRecipients : NavDestination("add-delivery-recipients", Icons.Outlined.PersonAdd, "Add Delivery")
    data object Account : NavDestination("account", Icons.Outlined.AccountCircle, "Account")
}

// The list of pages that appear in the bottom navigation
val bottomNavItems = listOf(
    NavDestination.Home,
    NavDestination.Login,
    NavDestination.FindDelivery,
    NavDestination.AddDelivery,
    NavDestination.Account,
)