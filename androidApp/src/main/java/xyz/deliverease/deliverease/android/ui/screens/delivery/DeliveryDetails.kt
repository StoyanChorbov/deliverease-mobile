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
import xyz.deliverease.deliverease.android.ui.display.MapWithLiveLocationAndMarkers
import xyz.deliverease.deliverease.delivery.Location
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsState
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsViewModel

@Composable
fun DeliveryDetailsScreenRoot(
    modifier: Modifier = Modifier,
    deliveryId: String,
) {
    // TODO: Check if there is a better implementation for this
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
    Column(modifier = modifier) {
        MapWithLiveLocationAndMarkers(
            points = setOf(deliveryDetailsState.startLocation, deliveryDetailsState.endLocation),
            liveLocation = Location("Name", 42.315073, 24.627979),
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