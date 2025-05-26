package xyz.deliverease.deliverease.delivery.details

sealed class DeliveryDetailsEvent {
    data object Idle : DeliveryDetailsEvent()
    data object DeliverPackage : DeliveryDetailsEvent()
    data object MarkAsDelivered : DeliveryDetailsEvent()
    data object ConnectToLocationServer : DeliveryDetailsEvent()
    data object DisconnectFromLocationServer : DeliveryDetailsEvent()
    data object RequestLocationUpdate : DeliveryDetailsEvent()
    data class TriggerLocationUpdate(val recipientUsername: String) : DeliveryDetailsEvent()
    data class ReceiveLocationUpdate(val latitude: Double, val longitude: Double) : DeliveryDetailsEvent()
    data class SendLocationUpdate(val recipientUsername: String, val latitude: Double, val longitude: Double) : DeliveryDetailsEvent()
}