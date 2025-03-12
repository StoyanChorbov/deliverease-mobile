package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.deliverease.deliverease.android.ui.display.DeliveryRow
import xyz.deliverease.deliverease.android.ui.display.DeliveryRowViewModel
import xyz.deliverease.deliverease.android.ui.display.TestMap

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TestMap()
        DeliveriesSection()
    }
}

@Composable
fun DeliveriesSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val deliveries = arrayOf(
            DeliveryRowViewModel(
                title = "Delivery 1",
                startLocation = "Start Location 1",
                endLocation = "End Location 1",
                image = Icons.Outlined.LocationOn
            ),
            DeliveryRowViewModel(
                title = "Delivery 2",
                startLocation = "Start Location 2",
                endLocation = "End Location 2",
                image = Icons.Outlined.LocationOn
            ),
            DeliveryRowViewModel(
                title = "Delivery 3",
                startLocation = "Start Location 3",
                endLocation = "End Location 3",
                image = Icons.Outlined.LocationOn
            ),
            DeliveryRowViewModel(
                title = "Delivery 4",
                startLocation = "Start Location 4",
                endLocation = "End Location 4",
                image = Icons.Outlined.LocationOn
            ),
            DeliveryRowViewModel(
                title = "Delivery 5",
                startLocation = "Start Location 5",
                endLocation = "End Location 5",
                image = Icons.Outlined.LocationOn
            ),
        )
        deliveries.forEach {
            DeliveryRow(delivery = it)
        }
    }
}