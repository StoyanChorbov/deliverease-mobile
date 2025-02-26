package xyz.deliverease.deliverease

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserService {
    private val client = HttpClient(getHttpEngine()) {
        install(ContentNegotiation) {
            json()
        }
    }
    private val baseUrl = getBaseUrl()

    suspend fun login(token: String = ""): UserDTO = runWithContext {
        //TODO("Get actual user with/without token")
        return@runWithContext client.get("$baseUrl/users/pesho").body()
    }

    suspend fun register(user: UserRegisterDTO): UserDTO = runWithContext {
        try {
            if (user.password == user.confirmPassword) {
                val res = client.post("$baseUrl/users/register") {
                    contentType(ContentType.Application.Json)
                    setBody(user)
                }
                if (res.status.value != 201)
                    throw Exception("Failed to register user")
                return@runWithContext res.body()
            } else
                throw Exception("Passwords do not match")
        } catch (e: Exception) {
            println(e.message)
            throw e
        }
    }

    private suspend fun <T> runWithContext(fn: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            return@withContext fn()
        }
}