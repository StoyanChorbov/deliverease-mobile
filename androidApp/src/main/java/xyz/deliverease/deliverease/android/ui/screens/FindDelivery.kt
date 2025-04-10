package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.deliverease.deliverease.android.ui.display.DeliveryRow
import xyz.deliverease.deliverease.delivery.find.FindableDelivery
import xyz.deliverease.deliverease.delivery.home.DeliveryListDTO

@Composable
fun FindDeliveryScreenRoot(modifier: Modifier = Modifier) {
    FindDeliveryScreen(modifier = modifier, deliveries = emptySet())
}

@Composable
fun FindDeliveryScreen(modifier: Modifier, deliveries: Set<DeliveryListDTO>) {
    Column(modifier = modifier) {
        FindableDeliveryScreenRoot()
//        if (deliveries.isNotEmpty()) {
//            deliveries.forEach {
////                FindableDeliveryCard(delivery = it)
//                DeliveryRow(
//                    delivery = it,
//                    handleNavigation = { /* TODO: Handle navigation */ },
//                )
//            }
//        } else {
//            Text(text = "No deliveries found")
//        }
    }
}

@Composable
fun FindableDeliveryCard(modifier: Modifier = Modifier, delivery: FindableDelivery) {
    Card(modifier = modifier) {
        Column {
            Text(text = delivery.name)
            Text(text = delivery.startingLocation.toString())
            Text(text = delivery.endingLocation.toString())
            Text(text = delivery.category.toString())
            Text(text = delivery.isFragile.toString())
        }
    }
}