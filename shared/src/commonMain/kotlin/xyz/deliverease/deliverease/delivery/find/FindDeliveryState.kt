package xyz.deliverease.deliverease.delivery.find

import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.delivery.LocationDto

data class FindDeliveryState(
    val results: List<DeliveryListDTO> = emptyList(),
    val destinationQuery: String = "",
    val destinationSuggestions: List<LocationDto> = emptyList(),
    val destination: LocationDto = LocationDto(),
    val currentLocation: LocationDto = LocationDto(),
    val isLoading: Boolean = false,
    val error: String? = null,
)