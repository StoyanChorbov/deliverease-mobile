package xyz.deliverease.deliverease.util.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "jwt_preferences")

actual class JwtTokenStorage(private val context: Context) {

    private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    actual suspend fun getJwtToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[AUTH_TOKEN_KEY]
    }

    actual suspend fun getRefreshToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[REFRESH_TOKEN_KEY]
    }

    actual suspend fun saveTokens(authToken: String, refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = authToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    actual suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

}