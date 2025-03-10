package xyz.deliverease.deliverease.delivery

data class AddDeliveryState(
    val delivery: DeliveryDTO = DeliveryDTO(),
    val loading: Boolean = false,
    val error: String? = null
)