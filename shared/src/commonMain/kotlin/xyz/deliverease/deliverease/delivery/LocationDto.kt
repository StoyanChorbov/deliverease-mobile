package xyz.deliverease.deliverease.delivery

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(val place: String = "", val street: String = "", val number: Int = 0, val region: String = "", val latitude: Double = 0.0, val longitude: Double = 0.0) {
    fun formattedAddress(): String {
        val result = StringBuilder()

        if (street.isNotBlank()) {
            if (number != 0)
                result.append("$street $number, ")
            else
                result.append("$street, ")
        }

        result.append("$place, $region")
        return result.toString()
    }
}
