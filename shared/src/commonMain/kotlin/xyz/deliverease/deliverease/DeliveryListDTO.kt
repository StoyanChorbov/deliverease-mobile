package xyz.deliverease.deliverease

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

@Serializable
data class DeliveryListDTO(
    val id: String,
    val name: String,
    val startingLocationDto: LocationDto,
    val endingLocationDto: LocationDto,
    val category: DeliveryCategory,
    val isFragile: Boolean = false,
)