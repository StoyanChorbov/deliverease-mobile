package xyz.deliverease.deliverease.user.pastDelivery

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory

@Serializable
data class UserDeliveryDTO(
    val id: String,
    val name: String,
    val startLocationRegion: String,
    val endLocationRegion: String,
    val category: DeliveryCategory,
    val isFragile: Boolean = false,
)
