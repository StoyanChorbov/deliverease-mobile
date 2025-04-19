package xyz.deliverease.deliverease.android.ui.screens.delivery

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.deliverease.deliverease.android.ui.display.MapWithMarkers
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsDTO
import xyz.deliverease.deliverease.util.validation.toPascalCase

@Composable
fun FindableDeliveryScreenRoot(modifier: Modifier = Modifier) {
    // TODO: Change with viewmodel
    val del = DeliveryDetailsDTO(
        id = "1",
        name = "Delivery Name",
        description = "Delivery Description",
        sender = "Sender",
        recipients = setOf("Recipient"),
        startingLocation = Location(
            name = "Start Location",
            latitude = 0.0,
            longitude = 0.0
        ),
        endingLocation = Location(
            name = "End Location",
            latitude = 1.0,
            longitude = 1.0
        ),
        category = DeliveryCategory.Food,
        isFragile = false
    )
    FindableDeliveryScreen(modifier = modifier, delivery = del)
}

@Composable
fun FindableDeliveryScreen(modifier: Modifier = Modifier, delivery: DeliveryDetailsDTO) {
    Column {
        MapWithMarkers(points = setOf(delivery.startingLocation, delivery.endingLocation))
        OutlinedCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, 12.dp)
            ) {
                Text(
                    text = "${delivery.name} (${delivery.category.name.toPascalCase()})",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${delivery.startingLocation.name} -> ${delivery.endingLocation.name}",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = if (delivery.isFragile) "Fragile" else "Not Fragile",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = delivery.description, style = MaterialTheme.typography.bodyLarge)
                Text(text = delivery.sender, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
