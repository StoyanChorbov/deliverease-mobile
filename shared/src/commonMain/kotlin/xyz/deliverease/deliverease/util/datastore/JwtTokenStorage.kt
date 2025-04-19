package xyz.deliverease.deliverease.util.datastore

expect class JwtTokenStorage {
    suspend fun getJwtToken(): String?
    suspend fun getRefreshToken(): String?

    suspend fun saveTokens(authToken: String, refreshToken: String)

    suspend fun clearTokens()
}