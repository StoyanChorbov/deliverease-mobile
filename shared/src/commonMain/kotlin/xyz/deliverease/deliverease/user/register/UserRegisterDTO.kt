package xyz.deliverease.deliverease.user.register

import kotlinx.serialization.Serializable

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