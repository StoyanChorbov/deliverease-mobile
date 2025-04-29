package xyz.deliverease.deliverease.android.ui.components.display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.deliverease.deliverease.DeliveryListDTO

@Composable
fun DeliveriesSection(
    modifier: Modifier = Modifier,
    label: String,
    deliveries: List<DeliveryListDTO>,
    handleNavigation: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        deliveries.forEach {
            DeliveryRow(delivery = it, handleNavigation = { handleNavigation(it.id) })
        }
    }
}