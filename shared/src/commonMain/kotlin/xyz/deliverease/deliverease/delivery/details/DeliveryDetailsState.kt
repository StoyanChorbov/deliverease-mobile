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
    val recipients: Set<String> = emptySet(),
    val isFragile: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasError: Boolean = false
)