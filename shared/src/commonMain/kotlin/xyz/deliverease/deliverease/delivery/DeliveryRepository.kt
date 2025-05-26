package xyz.deliverease.deliverease.delivery

import eu.lepicekmichal.signalrkore.HubConnection
import eu.lepicekmichal.signalrkore.HubConnectionBuilder
import eu.lepicekmichal.signalrkore.HubConnectionState
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
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.LocationQueryResponseDto
import xyz.deliverease.deliverease.delivery.add.AddDeliveryDTO
import xyz.deliverease.deliverease.delivery.details.DeliveryRequestDto
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsDTO
import xyz.deliverease.deliverease.delivery.home.CurrentDeliveriesResponseDto
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.delivery.past.UserDeliveryDTO
import xyz.deliverease.deliverease.util.datastore.JwtTokenDelegate
import xyz.deliverease.deliverease.util.datastore.JwtTokenStorage

expect fun getMapboxAccessToken(): String

class DeliveryRepository(
    private val jwtTokenStorage: JwtTokenStorage,
    private val userRepository: UserRepository
) : BaseRepository() {
    private val accessToken: String? by JwtTokenDelegate(jwtTokenStorage)
    private val connection: HubConnection =
        HubConnectionBuilder.create("$baseUrl/hubs/locations?access_token=$accessToken")

    suspend fun addDelivery(addDeliveryDTO: AddDeliveryDTO): String {
        val res = client.post {
            url("$baseUrl/deliveries")
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer $accessToken")
            }
            setBody(addDeliveryDTO)
        }
        if (!res.status.isSuccess())
            throw IllegalArgumentException("There was a problem with creating the delivery")

        return res.body<String>().trim('"')
    }

    suspend fun getDeliveryDetails(deliveryId: String): DeliveryDetailsDTO {
        val res = client.get {
            url("$baseUrl/deliveries/$deliveryId")
            headers {
                append("Authorization", "Bearer $accessToken")
            }
        }

        if (res.status != HttpStatusCode.OK) {
            throw Exception("error: ${res.body<String>()}")
        }

        return res.body()
    }

    suspend fun getDeliveryOptions(
        startingLocationRegion: String,
        endingLocationRegion: String
    ): List<DeliveryListDTO> {
        var res = client.get {
            url("$baseUrl/deliveries/options/$startingLocationRegion/$endingLocationRegion")
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer $accessToken")
            }
        }

        if (res.status == HttpStatusCode.Unauthorized) {
            userRepository.refreshTokens()
            res = client.get {
                url("$baseUrl/deliveries/options/$startingLocationRegion/$endingLocationRegion")
                contentType(ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }

        if (res.status != HttpStatusCode.OK) {
            throw Exception("Error: ${res.body<String>()}")
        }

        return res.body()
    }

    suspend fun getCurrentDeliveries(): CurrentDeliveriesResponseDto {
        var res = client.get {
            url("$baseUrl/deliveries/current")
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer $accessToken")
            }
        }

        if (res.status == HttpStatusCode.Unauthorized) {
            userRepository.refreshTokens()
            res = client.get {
                url("$baseUrl/deliveries/current")
                contentType(ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $accessToken")
                }
            }
        }

        if (res.status != HttpStatusCode.OK) {
            throw Exception("Error: ${res.body<String>()}")
        }

        return res.body()
    }

    suspend fun getPastDeliveries(): List<UserDeliveryDTO> {
        val res = client.get {
            url("$baseUrl/deliveries/past")
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer $accessToken")
            }
        }

        if (!res.status.isSuccess())
            throw Exception("There was an error")

        return res.body()
    }

    suspend fun deliverPackage(request: DeliveryRequestDto) {
        val res = client.post {
            url("$baseUrl/deliveries/deliver")
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer $accessToken")
            }
            setBody(request)
        }

        if (!res.status.isSuccess())
            throw Exception(res.body<String>())
    }

    suspend fun markAsDelivered(deliveryRequestDto: DeliveryRequestDto) {
        val res = client.post {
            url("$baseUrl/deliveries/completed")
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer $accessToken")
            }
            setBody(deliveryRequestDto)
        }

        if (!res.status.isSuccess())
            throw Exception(res.body<String>())
    }

    suspend fun getLocationByCoordinates(longitude: Double, latitude: Double): LocationDto {
        val mapboxToken = getMapboxAccessToken()

        val res = client.get {
            url(
                "https://api.mapbox.com/search/geocode/v6/reverse?" +
                        "longitude=$longitude&latitude=$latitude" +
                        "&access_token=$mapboxToken"
            )
        }

        if (!res.status.isSuccess())
            throw Exception("There was a problem with the query")

        return res.body<LocationQueryResponseDto>().features[0].let {
            val context = it.properties.context

            var number = context.address?.addressNumber?.toIntOrNull()
            if (number == null) number = 0

            var street = context.street?.name
            if (street == null) street = ""

            var place = context.place?.name
            if (place == null) place = ""

            var region = context.region?.name
            if (region == null) region = ""

            LocationDto(
                place = place,
                number = number,
                street = street,
                region = region,
                longitude = it.geometry.coordinates[0],
                latitude = it.geometry.coordinates[1]
            )
        }
    }

    suspend fun getLocationSuggestions(query: String): List<LocationDto> {
        val mapboxToken = getMapboxAccessToken()

        val res = client.get {
            url(
                "https://api.mapbox.com/search/geocode/v6/forward" +
                        "?q=$query" +
                        "&country=bg&types=address%2Csecondary_address%2Cstreet%2Cneighborhood%2Cplace%2Cdistrict%2Clocality%2Cpostcode" +
                        "&access_token=$mapboxToken"
            )
        }

        if (!res.status.isSuccess())
            throw Exception("There was a problem with the query")

        return res.body<LocationQueryResponseDto>().features.map {
            val context = it.properties.context

            var number = context.address?.addressNumber?.toIntOrNull()
            if (number == null) number = 0

            var street = context.street?.name
            if (street == null) street = ""

            var place = context.place?.name
            if (place == null) place = ""

            var region = context.region?.name
            if (region == null) region = ""

            LocationDto(
                place = place,
                number = number,
                street = street,
                region = region,
                longitude = it.geometry.coordinates[0],
                latitude = it.geometry.coordinates[1]
            )
        }
    }

    suspend fun requestLocationUpdate(
        delivererUsername: String
    ) {
        if (connection.connectionState.value == HubConnectionState.CONNECTED) {
            connection.invoke("RequestDelivererLocation", delivererUsername)
        } else {
            throw IllegalStateException("Connection is not established")
        }
    }

    suspend fun connectAsDeliverer(requestLocationUpdate: suspend (String) -> Unit) {
        if (connection.connectionState.value != HubConnectionState.CONNECTED) {
            connection.start()
        }
        connection.on("RequestLocationUpdate") { recipient: String ->
            requestLocationUpdate(recipient)
        }
    }

    suspend fun connectAsRecipient(
        onLocationUpdate: suspend (Double, Double) -> Unit
    ) {
        if (connection.connectionState.value != HubConnectionState.CONNECTED) {
            connection.start()
        }
        connection.on("UpdateDelivererLocation") { latitude: Double, longitude: Double ->
            onLocationUpdate(latitude, longitude)
        }
    }

    suspend fun sendLocationUpdate(recipient: String, latitude: Double, longitude: Double) {
        if (connection.connectionState.value == HubConnectionState.CONNECTED) {
            connection.invoke("ReceiveLocationUpdate", recipient, latitude, longitude)
        } else return
    }

    suspend fun stopSharingLocation() {
        connection.stop()
    }
}