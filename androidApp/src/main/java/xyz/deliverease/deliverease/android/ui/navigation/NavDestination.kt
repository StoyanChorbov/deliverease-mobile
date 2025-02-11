package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val icon: ImageVector, val label: String) {
    object Home : NavDestination("home", Icons.Outlined.Home, "Home")
    object Login : NavDestination("login", Icons.AutoMirrored.Outlined.Login, "Login")
    object Register : NavDestination("register", Icons.Outlined.PersonAdd, "Register")
    object Account : NavDestination("account", Icons.Outlined.AccountCircle, "Account")
}

val bottomNavItems = listOf(
    NavDestination.Home,
    NavDestination.Login,
    NavDestination.Register,
    NavDestination.Account,
)