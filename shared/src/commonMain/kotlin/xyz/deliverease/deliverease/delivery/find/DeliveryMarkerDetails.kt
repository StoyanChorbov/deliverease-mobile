package xyz.deliverease.deliverease.delivery.find

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.Location

@Serializable
data class DeliveryMarkerDetails(
    val id: String = "",
    val name: String = "",
    val startingLocation: Location = Location(),
    val endingLocation: Location = Location(),
)
