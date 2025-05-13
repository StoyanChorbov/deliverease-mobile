package xyz.deliverease.deliverease.android.ui.screens.delivery

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.components.display.DeliveryRow
import xyz.deliverease.deliverease.android.ui.components.display.LocationAutofill
import xyz.deliverease.deliverease.android.ui.location.currentLocation
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.delivery.find.FindDeliveryEvent
import xyz.deliverease.deliverease.delivery.find.FindDeliveryViewModel
import xyz.deliverease.deliverease.delivery.find.FindableDeliveryDto

@Composable
fun FindDeliveryScreenRoot(
    modifier: Modifier = Modifier,
    findDeliveryViewModel: FindDeliveryViewModel = koinViewModel()
) {
    val findDeliveryState by findDeliveryViewModel.findDeliveryState.collectAsState()
    val findDeliveryEvent by findDeliveryViewModel.findDeliveryEvent.collectAsState(
        FindDeliveryEvent.Idle
    )
    val navController = LocalNavController.current
    val location = currentLocation() ?: return

    FindDeliveryScreen(modifier = modifier,
        deliveries = findDeliveryState.results,
        locationInput = findDeliveryState.destinationQuery,
        locationSuggestions = findDeliveryState.destinationSuggestions,
        onUpdateLocationQuery = {
            findDeliveryViewModel.onEvent(
                FindDeliveryEvent.SetLocationQuery(
                    it
                )
            )
        },
        onSelectDestination = { findDeliveryViewModel.onEvent(FindDeliveryEvent.SetDestination(it)) },
        onFindDeliveries = {
            findDeliveryViewModel.onEvent(FindDeliveryEvent.GetDeliveryOptions(location.longitude, location.latitude))
        },
        handleNavigation = {
            navigateTo(navController, "NavDestination.DeliveryDetails.route/$it")
        }
    )
}

@Composable
fun FindDeliveryScreen(
    modifier: Modifier = Modifier,
    deliveries: List<DeliveryListDTO>,
    locationInput: String,
    locationSuggestions: List<LocationDto>,
    onUpdateLocationQuery: (String) -> Unit,
    onSelectDestination: (LocationDto) -> Unit,
    onFindDeliveries: () -> Unit,
    handleNavigation: (String) -> Unit
) {
    Column(modifier = modifier) {
        LocationAutofill(
            label = "Destination",
            input = locationInput,
            suggestions = locationSuggestions,
            onInputChange = onUpdateLocationQuery,
            onSelectedChange = onSelectDestination
        )
        Button(onClick = onFindDeliveries) {
            Text("Find deliveries")
        }
        if (deliveries.isNotEmpty()) {
            deliveries.forEach {
//                FindableDeliveryCard(delivery = it)
                DeliveryRow(
                    delivery = it,
                    handleNavigation = { handleNavigation(it.id) },
                )
            }
        } else {
            Text(text = "No deliveries found")
        }
    }
}

@Composable
fun FindableDeliveryCard(modifier: Modifier = Modifier, delivery: FindableDeliveryDto) {
    Card(modifier = modifier) {
        Column {
            Text(text = delivery.name)
            Text(text = delivery.startingLocationDto.toString())
            Text(text = delivery.endingLocationDto.toString())
            Text(text = delivery.category.toString())
            Text(text = delivery.isFragile.toString())
        }
    }
}