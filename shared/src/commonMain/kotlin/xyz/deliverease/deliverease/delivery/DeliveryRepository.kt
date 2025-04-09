package xyz.deliverease.deliverease.delivery

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import xyz.deliverease.deliverease.BaseRepository
import xyz.deliverease.deliverease.delivery.add.AddDeliveryDTO
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsDTO

class DeliveryRepository : BaseRepository() {

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
}