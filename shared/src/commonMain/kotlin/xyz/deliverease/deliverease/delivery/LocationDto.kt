package xyz.deliverease.deliverease.delivery

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(val name: String = "", val latitude: Double = 0.0, val longitude: Double = 0.0)