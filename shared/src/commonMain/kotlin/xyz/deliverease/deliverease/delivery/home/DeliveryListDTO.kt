package xyz.deliverease.deliverease.delivery.home

import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location

data class DeliveryListDTO(
    val id: String,
    val name: String,
    val startingLocation: Location,
    val endingLocation: Location,
    val category: DeliveryCategory
)