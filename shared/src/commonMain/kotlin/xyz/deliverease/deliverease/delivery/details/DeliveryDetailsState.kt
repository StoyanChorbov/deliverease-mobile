package xyz.deliverease.deliverease.delivery.details

import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

data class DeliveryDetailsState(
    val name: String = "",
    val startLocationDto: LocationDto = LocationDto(),
    val endLocationDto: LocationDto = LocationDto(),
    val description: String = "",
    val category: DeliveryCategory = DeliveryCategory.Other,
    val currentRecipient: String = "",
    val sender: String = "",
    val recipients: Set<String> = emptySet(),
    val deliverer: String? = null,
    val delivererLocation: LocationDto? = null,
    val user: String = "",
    val isFragile: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)