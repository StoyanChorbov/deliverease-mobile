package xyz.deliverease.deliverease

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

expect fun getHttpEngine(): HttpClientEngine
expect fun getBaseUrl(): String

object HttpClientProvider {
    val client: HttpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}