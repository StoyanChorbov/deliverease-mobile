package xyz.deliverease.deliverease.delivery.add

sealed class AddDeliveryEvent {
    data object Idle : AddDeliveryEvent()

    sealed class Input {
        data class AddRecipient(val value: String) : AddDeliveryEvent()
    }

    sealed class Navigate {
        // TODO: Add events for navigation if needed
    }

    // TODO: Remove event if needed
    data object Submit : AddDeliveryEvent()
    data class Error(val message: String) : AddDeliveryEvent()
}