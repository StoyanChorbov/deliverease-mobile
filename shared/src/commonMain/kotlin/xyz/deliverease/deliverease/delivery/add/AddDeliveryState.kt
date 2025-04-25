package xyz.deliverease.deliverease.delivery.add

import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

data class AddDeliveryState(
    val name: String = "",
    val startLocationDto: LocationDto = LocationDto(),
    val endLocationDto: LocationDto = LocationDto(),
    val description: String = "",
    val category: DeliveryCategory = DeliveryCategory.Other,
    val currentRecipient: String = "",
    val recipients: Set<String> = emptySet(),
    val isFragile: Boolean = false,
    val isNameValid: Boolean = true,
    val isStartLocationValid: Boolean = true,
    val isEndLocationValid: Boolean = true,
    val isDescriptionValid: Boolean = true,
    val isDeliveryCategoryValid: Boolean = true,
    val isCurrentRecipientValid: Boolean = true,
    val isInputValid: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasError: Boolean = false,
)