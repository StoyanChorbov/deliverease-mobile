package xyz.deliverease.deliverease.delivery.find

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

@Serializable
data class FindableDeliveryDto(
    val id: String,
    val name: String,
    val startingLocationDto: LocationDto,
    val endingLocationDto: LocationDto,
    val category: DeliveryCategory,
    val isFragile: Boolean = false,
)
