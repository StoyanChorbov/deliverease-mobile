package xyz.deliverease.deliverease.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val isDeliveryPerson: Boolean,
//    val imageUrl: String,
)
{
    constructor() : this("", "", "", "", "", false)
}

