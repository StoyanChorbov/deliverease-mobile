package xyz.deliverease.deliverease

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Classes for the response from the location autofill query

@Serializable
data class LocationQueryResponseDto(
    val type: String,
    val features: List<LocationQueryFeatureDTO>,
    val attribution: String
)

@Serializable
data class LocationQueryFeatureDTO(
    val id: String,
    val type: String,
    val geometry: LocationQueryPointDTO,
    val properties: LocationQueryPropertiesDto
)

@Serializable
data class LocationQueryPointDTO(val type: String, val coordinates: List<Double>)

@Serializable
data class LocationQueryPropertiesDto(
    // Mapping value from response to data class property
    @SerialName("mapbox_id") val mapboxId: String = "",
    @SerialName("feature_type") val featureType: String = "",
    val name: String = "",
    @SerialName("name_preferred") val namePreferred: String = "",
    @SerialName("place_formatted") val placeFormatted: String = "",
    @SerialName("full_address") val fullAddress: String = "",
    val context: LocationQueryContextDto
)

// Context data class that holds a large amount of the data needed for getting the location
@Serializable
data class LocationQueryContextDto(
    val address: LocationQueryContextAddressDto? = null,
    val street: LocationQueryContextStreetDto? = null,
    val postcode: LocationQueryContextPostcodeDto? = null,
    val place: LocationQueryContextPlaceDto? = null,
    val region: LocationQueryContextRegionDto? = null,
    val country: LocationQueryContextCountryDto? = null
)

@Serializable
data class LocationQueryContextAddressDto(
    @SerialName("mapbox_id") val mapboxId: String,
    @SerialName("address_number") val addressNumber: String,
    @SerialName("street_name") val streetName: String,
    val name: String
)

@Serializable
data class LocationQueryContextStreetDto(
    @SerialName("mapbox_id") val mapboxId: String,
    val name: String,
)

@Serializable
data class LocationQueryContextPostcodeDto(
    @SerialName("mapbox_id") val mapboxId: String,
    val name: String,
)

@Serializable
data class LocationQueryContextPlaceDto(
    @SerialName("mapbox_id") val mapboxId: String,
    val name: String,
    @SerialName("wikidata_id") val wikidataId: String = ""
)

@Serializable
data class LocationQueryContextRegionDto(
    @SerialName("mapbox_id") val mapboxId: String,
    val name: String,
    @SerialName("wikidata_id") val wikidataId: String = "",
    @SerialName("region_code") val regionCode: String = "",
    @SerialName("region_code_full") val fullRegionCode: String = ""
)

@Serializable
data class LocationQueryContextCountryDto(
    @SerialName("mapbox_id") val mapboxId: String,
    val name: String,
    @SerialName("wikidata_id") val wikidataId: String = "",
    @SerialName("country_code") val countryCode: String = "",
    @SerialName("country_code_alpha_3") val alphaCountryCode: String = ""
)