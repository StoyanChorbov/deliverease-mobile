package xyz.deliverease.deliverease.delivery.add

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

@Serializable
data class AddDeliveryDTO(
    val name: String,
    val startLocationDto: LocationDto,
    val endLocationDto: LocationDto,
    val description: String,
    val category: DeliveryCategory,
    val recipients: Set<String>,
    val isFragile: Boolean,
)