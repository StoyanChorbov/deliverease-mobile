package xyz.deliverease.deliverease.delivery.add

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

@Serializable
data class AddDeliveryDTO(
    val name: String,
    val description: String,
    val category: DeliveryCategory,
    val startLocation: LocationDto,
    val startLocationRegion: String,
    val endLocation: LocationDto,
    val endLocationRegion: String,
    val recipients: Set<String>,
    val isFragile: Boolean,
)