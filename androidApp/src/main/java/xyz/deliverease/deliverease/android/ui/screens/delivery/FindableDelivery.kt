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
import xyz.deliverease.deliverease.android.ui.components.display.MapWithMarkers
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsDTO
import xyz.deliverease.deliverease.util.toPascalCase

//@Composable
//fun FindableDeliveryScreenRoot(modifier: Modifier = Modifier) {
//    val del = viewModel.deliveryDetailsState.value
//    FindableDeliveryScreen(modifier = modifier, delivery = DeliveryDetailsDTO())
//}

//@Composable
//fun FindableDeliveryScreen(modifier: Modifier = Modifier, delivery: DeliveryDetailsDTO) {
//    Column {
//        MapWithMarkers(points = setOf(delivery.startingLocation, delivery.endingLocation))
//        OutlinedCard(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
//            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp, 12.dp)
//            ) {
//                Text(
//                    text = "${delivery.name} (${delivery.category.name.toPascalCase()})",
//                    style = MaterialTheme.typography.headlineMedium
//                )
//                Text(
//                    text = "${delivery.startingLocation.place} -> ${delivery.endingLocation.place}",
//                    style = MaterialTheme.typography.labelLarge
//                )
//                Text(
//                    text = if (delivery.isFragile) "Fragile" else "Not Fragile",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//                Text(text = delivery.description, style = MaterialTheme.typography.bodyLarge)
//                Text(text = delivery.sender, style = MaterialTheme.typography.bodyLarge)
//            }
//        }
//    }
//}
