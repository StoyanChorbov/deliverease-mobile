package xyz.deliverease.deliverease.delivery.find

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.LocationDto

@Serializable
data class DeliveryMarkerDetails(
    val id: String = "",
    val name: String = "",
    val startingLocationDto: LocationDto = LocationDto(),
    val endingLocationDto: LocationDto = LocationDto(),
)
