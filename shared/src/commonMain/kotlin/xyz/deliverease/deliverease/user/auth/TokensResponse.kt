package xyz.deliverease.deliverease.user.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokensResponse(val accessToken: String, val refreshToken: String)
