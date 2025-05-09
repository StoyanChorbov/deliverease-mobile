package xyz.deliverease.deliverease.util.datastore

import kotlinx.coroutines.flow.Flow

expect class JwtTokenStorage {
    val authTokenFlow: Flow<String?>

    suspend fun getJwtToken(): String?
    suspend fun getRefreshToken(): String?

    suspend fun saveTokens(authToken: String, refreshToken: String)

    suspend fun clearTokens()
}