package xyz.deliverease.deliverease.delivery

import kotlinx.serialization.Serializable

@Serializable
enum class DeliveryCategory {
    FOOD,
    CLOTHES,
    ELECTRONICS,
    OTHER
}