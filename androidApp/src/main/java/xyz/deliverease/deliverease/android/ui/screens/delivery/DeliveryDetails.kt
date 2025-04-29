package xyz.deliverease.deliverease.android.ui.screens.delivery

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.deliverease.deliverease.android.ui.components.display.MapWithLiveLocationAndMarkers
import xyz.deliverease.deliverease.android.ui.location.currentLocation
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsState
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsViewModel

@Composable
fun DeliveryDetailsScreenRoot(
    modifier: Modifier = Modifier,
    deliveryId: String,
) {
    val deliveryViewModel: DeliveryDetailsViewModel =
        koinViewModel(parameters = { parametersOf(deliveryId) })
    val deliveryDetailsState by deliveryViewModel.deliveryDetailsState.collectAsState()
    DeliveryDetailsScreen(
        modifier = modifier,
        deliveryDetailsState = deliveryDetailsState
    )
}

@Composable
fun DeliveryDetailsScreen(
    modifier: Modifier = Modifier,
    deliveryDetailsState: DeliveryDetailsState
) {
    val location = currentLocation() ?: return
    Column(modifier = modifier) {
        MapWithLiveLocationAndMarkers(
            points = setOf(deliveryDetailsState.startLocationDto, deliveryDetailsState.endLocationDto),
            liveLocationDto = LocationDto("Name", "", location.latitude, location.longitude),
        )
        Text(
            text = "Name: ${deliveryDetailsState.name}",
            style = MaterialTheme.typography.titleMedium
        )
        deliveryDetailsState.recipients.forEach {
            Text(text = "Recipient: $it", style = MaterialTheme.typography.labelMedium)
        }
        Text(
            text = "Category: ${deliveryDetailsState.category.name}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}