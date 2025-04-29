package xyz.deliverease.deliverease.delivery.add

import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto

sealed class AddDeliveryEvent {
    data object Idle : AddDeliveryEvent()

    sealed class Input {
        data class SetName(val value: String) : AddDeliveryEvent()
        data class SetDescription(val value: String) : AddDeliveryEvent()
        data class SetDeliveryCategory(val value: DeliveryCategory) : AddDeliveryEvent()
        data class SetStartLocation(val place: LocationDto, val region: String) : AddDeliveryEvent()
        data class SetEndLocation(val place: LocationDto, val region: String) : AddDeliveryEvent()
        data class ChangeCurrentRecipient(val value: String) : AddDeliveryEvent()
        data object AddRecipient : AddDeliveryEvent()
        data class RemoveRecipient(val value: String) : AddDeliveryEvent()
        data class SetIsFragile(val value: Boolean) : AddDeliveryEvent()
    }

    data object Navigate : AddDeliveryEvent()

    data object Submit : AddDeliveryEvent()
    data class Error(val message: String) : AddDeliveryEvent()
}