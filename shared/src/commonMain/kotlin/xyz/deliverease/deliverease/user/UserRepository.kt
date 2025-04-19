package xyz.deliverease.deliverease.user

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import xyz.deliverease.deliverease.BaseRepository
import xyz.deliverease.deliverease.user.auth.TokensResponse
import xyz.deliverease.deliverease.user.login.UserLoginDTO
import xyz.deliverease.deliverease.user.profile.ProfileDTO
import xyz.deliverease.deliverease.user.register.UserRegisterDTO
import xyz.deliverease.deliverease.util.datastore.JwtTokenStorage

class UserRepository(
    private val jwtTokenStorage: JwtTokenStorage
) : BaseRepository() {
    suspend fun login(userDto: UserLoginDTO) {
        val res = client.post {
            url("$baseUrl/users/login")
            contentType(ContentType.Application.Json)
            setBody(userDto)
        }
        if (!res.status.isSuccess())
            throw Exception("Failed to login")

        val tokens: TokensResponse = res.body()
        jwtTokenStorage.saveTokens(
            authToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )
    }

    suspend fun register(user: UserRegisterDTO) {
        if (user.password != user.confirmPassword)
            throw IllegalArgumentException("Passwords do not match")

        val res = client.post("$baseUrl/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }

        if (!res.status.isSuccess())
            throw Exception("Failed to register user")
    }

    suspend fun getProfile(): ProfileDTO? {
        val authToken =
            jwtTokenStorage.getJwtToken() ?: throw IllegalArgumentException("No auth token found")

        var res = client.get {
            url("$baseUrl/users/profile")
            contentType(ContentType.Application.Json)
            headers {
                append("Bearer", authToken)
            }
        }

        if (res.status == HttpStatusCode.Unauthorized) {
            val refreshToken = jwtTokenStorage.getRefreshToken()
                ?: throw IllegalArgumentException("No refresh token found")
            res = client.get {
                url("$baseUrl/users/refresh")
                contentType(ContentType.Application.Json)
                headers {
                    append("Bearer", refreshToken)
                }
            }
        }

        if (!res.status.isSuccess())
            throw Exception("Failed to get profile")

        return res.body()
    }
}