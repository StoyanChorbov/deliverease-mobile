package xyz.deliverease.deliverease.delivery

private var mapboxAccessToken: String? = null

fun initializeDeliveryConfig(mapboxToken: String) {
    mapboxAccessToken = mapboxToken
}

actual fun getMapboxAccessToken(): String {
    if (mapboxAccessToken == null)
        throw IllegalArgumentException("Mapbox key not found")

    return mapboxAccessToken!!
}