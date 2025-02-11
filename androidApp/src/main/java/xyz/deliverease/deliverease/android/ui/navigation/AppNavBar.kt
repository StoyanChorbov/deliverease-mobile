package xyz.deliverease.deliverease.android.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import xyz.deliverease.deliverease.android.navigateTo


@Composable
fun NavBar(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = modifier
    )
    {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { destination ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.label,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    label = { Text(destination.label) },
                    selected = currentRoute == destination.route,
                    onClick = {
                        if (currentRoute != destination.route) {
                            navigateTo(navController, destination.route)
                        }
                    }
                )
            }
        }
    }
}