package xyz.deliverease.deliverease.user

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import xyz.deliverease.deliverease.BaseRepository

class UserRepository : BaseRepository() {

    suspend fun login(username: String = ""): UserDTO {
        //TODO("Get actual user with/without token")
        try {
            return client.get("$baseUrl/users/$username").body()
        } catch (e: Exception) {
            // TODO("Handle errors")
            throw e
        }
    }

    suspend fun register(user: UserRegisterDTO): UserDTO {
        try {
            if (user.password == user.confirmPassword) {
                val res = client.post("$baseUrl/users/register") {
                    contentType(ContentType.Application.Json)
                    setBody(user)
                }
                if (res.status.value != 201)
                    throw Exception("Failed to register user")
                return res.body()
            } else
                throw Exception("Passwords do not match")
        } catch (e: Exception) {
            println(e.message)
            throw e
        }
    }
}