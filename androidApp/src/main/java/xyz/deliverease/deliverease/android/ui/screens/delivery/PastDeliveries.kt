package xyz.deliverease.deliverease.android.ui.screens.delivery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.delivery.past.PastDeliveryState
import xyz.deliverease.deliverease.delivery.past.PastDeliveriesViewModel
import xyz.deliverease.deliverease.delivery.past.UserDeliveryDTO

@Composable
fun PastDeliveriesScreenRoot(
    modifier: Modifier = Modifier,
    pastDeliveriesViewModel: PastDeliveriesViewModel = koinViewModel()
) {
    val deliveryState by pastDeliveriesViewModel.pastDeliveryState.collectAsState()
    val navController = LocalNavController.current

    fun handleNavigation(deliveryId: String) {
        navigateTo(navController, "${NavDestination.DeliveryDetails.route}/$deliveryId")
    }

    PastDeliveriesScreen(
        modifier = modifier,
        deliveryState = deliveryState,
        handleNavigation = { handleNavigation(it) }
    )
}

@Composable
fun PastDeliveriesScreen(
    modifier: Modifier = Modifier,
    deliveryState: PastDeliveryState,
    handleNavigation: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Previously sent packages",
            style = MaterialTheme.typography.titleLarge
        )
        deliveryState.deliveries.forEach {
            PastDeliveryRow(
                delivery = it,
                handleNavigation = { handleNavigation(it.id) }
            )
        }
    }
}

@Composable
fun PastDeliveryRow(
    modifier: Modifier = Modifier,
    delivery: UserDeliveryDTO,
    handleNavigation: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
            .border(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp),
                width = 2.dp
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        onClick = handleNavigation,
    ) {
        Column(modifier = modifier) {
            Text(
                text = delivery.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${delivery.startLocationRegion} -> ${delivery.endLocationRegion}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}