package xyz.deliverease.deliverease.android.ui.display

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

data class DeliveryRowViewModel(
    val title: String,
    val startLocation: String,
    val endLocation: String,
    val image: ImageVector
) : ViewModel()

@Composable
fun DeliveryRow(modifier: Modifier = Modifier, delivery: DeliveryRowViewModel = viewModel()) {
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
            title = delivery.title,
            startLocation = delivery.startLocation,
            endLocation = delivery.endLocation
        )
        Image(
            imageVector = delivery.image,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            contentDescription = delivery.title
        )
    }
}

@Composable
fun DeliveryRowTitle(
    modifier: Modifier = Modifier,
    title: String,
    startLocation: String,
    endLocation: String
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