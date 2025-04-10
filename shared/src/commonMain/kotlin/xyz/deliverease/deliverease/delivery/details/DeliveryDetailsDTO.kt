package xyz.deliverease.deliverease.delivery.details

import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location

data class DeliveryDetailsDTO(
    val id: String,
    val name: String,
    val description: String,
    val sender: String,
    val recipients: Set<String>,
    val startingLocation: Location,
    val endingLocation: Location,
    val category: DeliveryCategory,
    val isFragile: Boolean,
)