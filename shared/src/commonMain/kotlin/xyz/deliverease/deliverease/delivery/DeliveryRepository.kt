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
import io.ktor.http.isSuccess
import xyz.deliverease.deliverease.BaseRepository
import xyz.deliverease.deliverease.DeliveryListDTO
import xyz.deliverease.deliverease.LocationQueryResponseDto
import xyz.deliverease.deliverease.delivery.add.AddDeliveryDTO
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsDTO
import xyz.deliverease.deliverease.delivery.home.CurrentDeliveriesResponseDto
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.user.pastDelivery.UserDeliveryDTO
import xyz.deliverease.deliverease.util.datastore.JwtTokenStorage

expect fun getMapboxAccessToken(): String

class DeliveryRepository(
    private val jwtTokenStorage: JwtTokenStorage,
    private val userRepository: UserRepository
) : BaseRepository() {
    //TODO: Rework refresh logic, since it refreshes tokens but doesn't update in the middle of the function

    suspend fun addDelivery(addDeliveryDTO: AddDeliveryDTO): String {
        val accessToken = jwtTokenStorage.getJwtToken()

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
        val accessToken = jwtTokenStorage.getJwtToken()

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
        val accessToken = jwtTokenStorage.getJwtToken()
            ?: throw IllegalArgumentException("No auth token found")

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
        val accessToken = jwtTokenStorage.getJwtToken()
            ?: throw IllegalArgumentException("No auth token found")

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
        val accessToken = jwtTokenStorage.getJwtToken()
            ?: throw IllegalArgumentException("No auth token found")

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

    suspend fun getLocationByCoordinates(longitude: Double, latitude: Double): LocationDto {
        val accessToken = getMapboxAccessToken()

        val res = client.get {
            url(
                "https://api.mapbox.com/search/geocode/v6/reverse?" +
                        "longitude=$longitude&latitude=$latitude" +
                        "&access_token=$accessToken"
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
        val accessToken = getMapboxAccessToken()

        val res = client.get {
            url(
                "https://api.mapbox.com/search/geocode/v6/forward" +
                        "?q=$query" +
                        "&types=address%2Csecondary_address%2Cstreet%2Cneighborhood%2Cplace%2Cdistrict%2Clocality%2Cpostcode" +
                        "&access_token=$accessToken"
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
}