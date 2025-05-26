package xyz.deliverease.deliverease.android.ui.screens.delivery

import android.content.Intent
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.deliverease.deliverease.android.ui.components.display.MapWithLiveLocationAndMarkers
import xyz.deliverease.deliverease.android.ui.location.ForegroundLocationService
import xyz.deliverease.deliverease.android.ui.location.currentLocation
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsEvent
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
    val deliveryDetailsEvent by deliveryViewModel.deliveryDetailsEvent.collectAsState(initial = DeliveryDetailsEvent.Idle)

    val location = currentLocation() ?: return
    val context = LocalContext.current

    LaunchedEffect(deliveryDetailsEvent) {
        when (deliveryDetailsEvent) {
            is DeliveryDetailsEvent.TriggerLocationUpdate -> {
                deliveryViewModel.onEvent(
                    DeliveryDetailsEvent.SendLocationUpdate(
                        recipientUsername = (deliveryDetailsEvent as DeliveryDetailsEvent.TriggerLocationUpdate).recipientUsername,
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
            }

            else -> {}
        }
    }

    DeliveryDetailsScreen(
        modifier = modifier,
        deliveryDetailsState = deliveryDetailsState,
        markAsDelivered = { deliveryViewModel.onEvent(DeliveryDetailsEvent.MarkAsDelivered) },
        startSharingLocation = {
            Intent(
                context,
                ForegroundLocationService::class.java
            ).apply {
                action = ForegroundLocationService.ACTION_START
                deliveryViewModel.onEvent(DeliveryDetailsEvent.ConnectToLocationServer)
                context.startService(this)
            }
        },
        stopSharingLocation = {
            Intent(
                context,
                ForegroundLocationService::class.java
            ).apply {
                action = ForegroundLocationService.ACTION_STOP
                deliveryViewModel.onEvent(DeliveryDetailsEvent.DisconnectFromLocationServer)
                context.startService(this)
            }
        },
        requestLocation = {
            deliveryViewModel.onEvent(DeliveryDetailsEvent.RequestLocationUpdate)
        },
        onDeliverPackage = { deliveryViewModel.onEvent(DeliveryDetailsEvent.DeliverPackage) }
    )
}

@Composable
fun DeliveryDetailsScreen(
    modifier: Modifier = Modifier,
    deliveryDetailsState: DeliveryDetailsState,
    markAsDelivered: () -> Unit,
    startSharingLocation: () -> Unit,
    stopSharingLocation: () -> Unit,
    requestLocation: () -> Unit,
    onDeliverPackage: () -> Unit
) {
    Column(modifier = modifier) {
        MapWithLiveLocationAndMarkers(
            points = setOf(
                deliveryDetailsState.startLocationDto,
                deliveryDetailsState.endLocationDto
            ),
            liveLocationDto = deliveryDetailsState.delivererLocation,
        )
        Row {
            Button(onClick = startSharingLocation) {
                Text("Connect")
            }
            Spacer(modifier.width(4.dp))
            Button(onClick = stopSharingLocation) {
                Text("Disconnect")
            }
        }
        if (deliveryDetailsState.deliverer != deliveryDetailsState.user) {
            Button(onClick = requestLocation) {
                Text("Request Location")
            }
        } else {
            Button(onClick = markAsDelivered) {
                Text("Mark as delivered")
            }
        }
        Text(
            text = "Name: ${deliveryDetailsState.name}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Category: ${deliveryDetailsState.category.name}",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = "Recipients: ${
                deliveryDetailsState.recipients.joinToString(
                    separator = ", ",
                    limit = 3
                )
            }", style = MaterialTheme.typography.bodyLarge
        )
        if (deliveryDetailsState.deliverer == null
            && deliveryDetailsState.sender != deliveryDetailsState.user
            && !deliveryDetailsState.recipients.contains(deliveryDetailsState.user)
        ) {
            ExtendedFloatingActionButton(
                onClick = onDeliverPackage,
                icon = { Icon(Icons.Outlined.LocalShipping, "Deliver package") },
                text = { Text("Deliver package") }
            )
        }
        if (deliveryDetailsState.error != null) {
            Text(
                text = deliveryDetailsState.error!!,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}