package xyz.deliverease.deliverease.delivery

import android.util.Log

actual fun getMapboxToken(): String {
    val token = System.getenv("MAPBOX_PUBLIC_TOKEN")
    Log.d("MapboxToken", "Token: $token")
    return token ?: throw IllegalStateException("Mapbox token not found")
}