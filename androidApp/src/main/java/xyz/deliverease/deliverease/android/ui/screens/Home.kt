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
import xyz.deliverease.deliverease.android.ui.components.display.DeliveriesSection
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.android.ui.components.display.MapWithLiveLocationAndMarkers
import xyz.deliverease.deliverease.android.ui.location.currentLocation
import xyz.deliverease.deliverease.delivery.home.HomeViewModel

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
        val location = currentLocation() ?: return
        MapWithLiveLocationAndMarkers(
            points = ((toDeliver + toReceive)
                .map { it.startingLocationDto } +
                    (toDeliver + toReceive)
                        .map { it.endingLocationDto })
                .toSet(),
            liveLocationDto = LocationDto(
                place = "Karlovo",
                region = "Plovdiv",
                latitude = location.latitude,
                longitude = location.longitude
            ),
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