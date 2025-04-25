package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.display.MapWithMarkers
import xyz.deliverease.deliverease.android.ui.display.DeliveriesSection
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.delivery.home.HomeViewModel
import java.util.UUID

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    toDeliver: List<DeliveryListDTO>,
    toReceive: List<DeliveryListDTO>,
    handleNavigation: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MapWithMarkers(
            points = setOf(
                LocationDto(name = "Start Location 1", latitude = 42.315073, longitude = 24.627979),
                LocationDto(name = "End Location 1", latitude = 44.2, longitude = 25.2),
                LocationDto(name = "Start Location 2", latitude = 45.4, longitude = 21.2),
                LocationDto(name = "End Location 2", latitude = 43.2, longitude = 22.2),
                LocationDto(name = "Start Location 3", latitude = 41.2, longitude = 23.2),
                LocationDto(name = "End Location 3", latitude = 40.2, longitude = 24.2),
            )
        )
        DeliveriesSection(
            label = "Packages to deliver",
            deliveries = toDeliver,
            handleNavigation = handleNavigation
        )
        DeliveriesSection(
            label = "Packages to receive",
            deliveries = toReceive,
            handleNavigation = handleNavigation
        )
    }
}

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val navController = LocalNavController.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isVisible = currentBackStackEntry?.destination?.route == "home"

    val state by homeViewModel.homeState.collectAsState()

    // TODO: Move to page with map
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        HomeScreen(
            toDeliver = state.toDeliver,
            toReceive = state.toReceive,
            handleNavigation = { deliveryId ->
                navigateTo(
                    navController,
                    "${NavDestination.DeliveryDetails.route}/$deliveryId"
                )
            }
        )
    }
}