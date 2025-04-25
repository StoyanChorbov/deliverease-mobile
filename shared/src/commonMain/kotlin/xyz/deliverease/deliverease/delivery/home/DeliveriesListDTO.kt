package xyz.deliverease.deliverease.delivery.home

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.DeliveryListDTO

@Serializable
data class DeliveriesListDTO(
    val toDeliver: List<DeliveryListDTO>,
    val toReceive: List<DeliveryListDTO>,
)