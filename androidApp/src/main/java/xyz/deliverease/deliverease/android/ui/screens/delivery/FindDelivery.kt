package xyz.deliverease.deliverease.android.ui.screens.delivery

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.components.display.DeliveryRow
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.delivery.find.FindableDeliveryDto

@Composable
fun FindDeliveryScreenRoot(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    FindDeliveryScreen(modifier = modifier, deliveries = emptySet(), handleNavigation = {
        navigateTo(navController, "NavDestination.DeliveryDetails.route/$it")
    })
}

@Composable
fun FindDeliveryScreen(modifier: Modifier, deliveries: Set<DeliveryListDTO>, handleNavigation: (String) -> Unit) {
    Column(modifier = modifier) {
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