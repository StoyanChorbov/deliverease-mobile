package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.deliverease.deliverease.android.ui.display.BasicMapWithMarkers
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsViewModel

@Composable
fun DeliveryDetailsScreen(
    modifier: Modifier = Modifier,
    deliveryId: String
) {
    //TODO: Get data from backend
    val delivery: DeliveryDetailsViewModel
}

@Composable
fun DeliveryDetails(
    modifier: Modifier = Modifier,
    name: String,
    startingLocation: Location,
    endingLocation: Location,
    recipients: Set<String>,
    category: DeliveryCategory
) {
    Column(modifier = modifier) {
        Text(text = "Name: $name", style = MaterialTheme.typography.titleMedium)
        recipients.forEach {
            Text(text = "Recipient: $it", style = MaterialTheme.typography.labelMedium)
        }
        Text(text = "Category: ${category.name}", style = MaterialTheme.typography.bodyMedium)
        BasicMapWithMarkers(
            points = setOf(startingLocation, endingLocation)
        )
    }
}