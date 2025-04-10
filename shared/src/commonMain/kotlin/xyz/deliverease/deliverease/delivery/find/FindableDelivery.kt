package xyz.deliverease.deliverease.delivery.find

import kotlinx.serialization.Serializable
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location

@Serializable
data class FindableDelivery(
    val id: String,
    val name: String,
    val startingLocation: Location,
    val endingLocation: Location,
    val category: DeliveryCategory,
    val isFragile: Boolean = false,
)
