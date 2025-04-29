package xyz.deliverease.deliverease

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

open class BaseRepository {
    protected val client = HttpClient(getHttpEngine()) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    encodeDefaults = true
                }
            )
        }
    }
    protected val baseUrl = getBaseUrl()
}