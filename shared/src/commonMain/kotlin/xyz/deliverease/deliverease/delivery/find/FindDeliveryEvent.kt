package xyz.deliverease.deliverease.delivery.find

import xyz.deliverease.deliverease.delivery.LocationDto

sealed class FindDeliveryEvent {
    data object Idle : FindDeliveryEvent()
    data class SetLocationQuery(val value: String) : FindDeliveryEvent()
    data class SetDestination(val value: LocationDto) : FindDeliveryEvent()
    data class GetDeliveryOptions(val startingLocationLongitude: Double, val startingLocationLatitude: Double) : FindDeliveryEvent()
    data class Error(val value: String) : FindDeliveryEvent()
    data class NavigateToDeliveryDetails(val deliveryId: String) : FindDeliveryEvent()
}