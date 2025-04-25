package xyz.deliverease.deliverease.delivery.details

import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

data class DeliveryDetailsDTO(
    val id: String,
    val name: String,
    val description: String,
    val sender: String,
    val recipients: Set<String>,
    val startingLocationDto: LocationDto,
    val endingLocationDto: LocationDto,
    val category: DeliveryCategory,
    val isFragile: Boolean,
)