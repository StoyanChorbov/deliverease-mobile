package xyz.deliverease.deliverease.delivery.details

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

@Serializable
data class DeliveryDetailsDTO(
    val id: String,
    val name: String,
    val description: String,
    val sender: String,
    val recipients: Set<String>,
    val startingLocation: LocationDto,
    val endingLocation: LocationDto,
    val category: DeliveryCategory,
    val isFragile: Boolean,
)