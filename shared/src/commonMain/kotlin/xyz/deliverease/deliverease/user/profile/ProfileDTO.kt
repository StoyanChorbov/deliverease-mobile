package xyz.deliverease.deliverease.user.profile

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

// Data Transfer Object for user profile
@Serializable
data class ProfileDTO(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
//    val imageUrl: String,
) {
    constructor() : this("", "", "", "", "")
}