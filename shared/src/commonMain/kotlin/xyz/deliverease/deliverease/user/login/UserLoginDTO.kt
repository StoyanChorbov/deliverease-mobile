package xyz.deliverease.deliverease.user.login

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDTO(
    val username: String,
    val password: String,
)
