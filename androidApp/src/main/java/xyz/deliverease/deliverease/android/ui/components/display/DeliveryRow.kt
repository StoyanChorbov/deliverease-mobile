package xyz.deliverease.deliverease.android.ui.components.display

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.util.toPascalCase

@Composable
fun DeliveryRow(
    modifier: Modifier = Modifier,
    delivery: DeliveryListDTO,
    handleNavigation: () -> Unit
) {
    Row(
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DeliveryRowTitle(
            title = "${delivery.name} (${delivery.category.toPascalCase()})" +
                    if (delivery.isFragile) " (fragile)" else "",
            startLocation = delivery.startingLocationDto.region,
            endLocation = delivery.endingLocationDto.region,
            onClick = handleNavigation
        )
    }
}

@Composable
fun DeliveryRowTitle(
    modifier: Modifier = Modifier,
    title: String,
    startLocation: String,
    endLocation: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
    ) {
        Column(modifier = modifier) {
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier,
                text = "$startLocation -> $endLocation",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}