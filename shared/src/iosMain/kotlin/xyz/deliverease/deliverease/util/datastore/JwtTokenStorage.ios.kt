package xyz.deliverease.deliverease.util.datastore

actual class JwtTokenStorage {
    actual suspend fun getJwtToken(): String? {
        return ""
    }

    actual suspend fun getRefreshToken(): String? {
        return ""
    }

    actual suspend fun saveTokens(authToken: String, refreshToken: String) {
    }

    actual suspend fun clearTokens() {
    }
}