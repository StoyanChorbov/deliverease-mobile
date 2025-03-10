package xyz.deliverease.deliverease.delivery

import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import xyz.deliverease.deliverease.BaseRepository

class DeliveryRepository : BaseRepository() {

    suspend fun addDelivery(deliveryDTO: DeliveryDTO): HttpStatusCode =
        client.post {  }.status
}