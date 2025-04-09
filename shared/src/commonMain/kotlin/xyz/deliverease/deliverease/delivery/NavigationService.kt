package xyz.deliverease.deliverease.delivery

import xyz.deliverease.deliverease.BaseRepository

expect fun getMapboxToken(): String

class NavigationService : BaseRepository() {

    private val MAPBOX_TOKEN: String = getMapboxToken()
    private val mapboxUrl = "https://api.mapbox.com/directions/v5/mapbox/driving/44.2,25.2;43.2,22.2?access_token=$MAPBOX_TOKEN"

    suspend fun getDirections(
        start: Location,
        end: Location,
        waypoints: List<Location> = emptyList(),
    ): String {
        val url = "$baseUrl/directions?start=${start.latitude},${start.longitude}&end=${end.latitude},${end.longitude}"
        val waypointsString = waypoints.joinToString(separator = "|") { "${it.latitude},${it.longitude}" }
        return if (waypoints.isNotEmpty()) {
            "$url&waypoints=$waypointsString"
        } else {
            url
        }
    }
}