package xyz.deliverease.deliverease.delivery

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import xyz.deliverease.deliverease.BaseRepository
import xyz.deliverease.deliverease.delivery.add.AddDeliveryDTO
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsDTO
import xyz.deliverease.deliverease.delivery.home.DeliveriesListDTO
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.util.datastore.JwtTokenStorage

class DeliveryRepository(
    private val jwtTokenStorage: JwtTokenStorage,
    private val userRepository: UserRepository
) : BaseRepository() {

    suspend fun addDelivery(addDeliveryDTO: AddDeliveryDTO) {
        val res = client.post {
            url("$baseUrl/delivery")
            contentType(ContentType.Application.Json)
            setBody(addDeliveryDTO)
        }
    }

    suspend fun getDeliveryDetails(deliveryId: String): DeliveryDetailsDTO {
        val res = client.get {
            url("$baseUrl/delivery/$deliveryId")
        }

        if (res.status != HttpStatusCode.OK) {
            throw Exception("error: ${res.body<String>()}")
        }

        return res.body()
    }

    suspend fun getAllDeliveries(): DeliveriesListDTO {
        val accessToken = jwtTokenStorage.getJwtToken()
            ?: throw IllegalArgumentException("No auth token found")

        var res = client.get {
            url("$baseUrl/deliveries")
            contentType(ContentType.Application.Json)
            headers {
                append("Bearer", accessToken)
            }
        }

        if (res.status == HttpStatusCode.Unauthorized) {
            userRepository.refreshTokens()

            res = client.get {
                url("$baseUrl/deliveries")
                contentType(ContentType.Application.Json)
                headers {
                    append("Bearer", accessToken)
                }
            }
        }

        if (res.status != HttpStatusCode.OK) {
            throw Exception("Error: ${res.body<String>()}")
        }

        return res.body()
    }
}