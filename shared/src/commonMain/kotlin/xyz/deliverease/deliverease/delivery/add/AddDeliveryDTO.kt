package xyz.deliverease.deliverease.delivery.add

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location

@Serializable
data class AddDeliveryDTO(
    val name: String,
    val startLocation: Location,
    val endLocation: Location,
    val description: String,
    val category: DeliveryCategory,
    val recipients: Set<String>,
    val isFragile: Boolean,
)