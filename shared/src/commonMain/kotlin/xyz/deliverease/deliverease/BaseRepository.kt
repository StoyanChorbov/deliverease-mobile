package xyz.deliverease.deliverease

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

open class BaseRepository {
    protected val client = HttpClient(getHttpEngine()) {
        install(ContentNegotiation) {
            json()
        }
    }
    protected val baseUrl = getBaseUrl()
}