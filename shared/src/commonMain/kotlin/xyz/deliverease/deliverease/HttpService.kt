package xyz.deliverease.deliverease

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class HttpService {
    private val client = HttpClient(getHttpEngine()) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val baseUrl = getBaseUrl()

    suspend fun login(token: String = ""): HttpResponse {
//        TODO("Get actual user with/without token")
        return client.get("$baseUrl/users/ivan")
    }

    suspend fun register(user: UserRegisterDTO): HttpResponse {
        try {
            if (user.password == user.confirmPassword) {
                val res = client.post("$baseUrl/users/register") {
                    contentType(ContentType.Application.Json)
                    setBody(user)
                }
                println(res.bodyAsText())
                return res
            } else
                throw Exception("Passwords do not match")
        } catch (e: Exception) {
            println(e.message)
            throw e
        }
    }
}

expect fun getHttpEngine(): HttpClientEngine
expect fun getBaseUrl(): String