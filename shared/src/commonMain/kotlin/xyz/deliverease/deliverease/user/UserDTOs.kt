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

@Serializable
data class UserRegisterDTO(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)

@Serializable
data class UserLoginDTO(
    val username: String,
    val password: String
)