package xyz.deliverease.deliverease.android.ui.components.display

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.search.autocomplete.PlaceAutocomplete
import com.mapbox.search.autocomplete.PlaceAutocompleteAddress
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.android.ui.components.input.AddressDropdown
import xyz.deliverease.deliverease.delivery.LocationDto

@Composable
fun LocationAutofill(
    modifier: Modifier = Modifier,
    label: String,
    setLocation: (LocationDto, String) -> Unit,
) {
    val autocomplete = PlaceAutocomplete.create()
    var input by remember { mutableStateOf("") }
    var isQueryTooShort by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var suggestions by remember { mutableStateOf<Map<PlaceAutocompleteAddress, Point>>(emptyMap()) }

    LaunchedEffect(input) {
        if (input.length < 2) {
            isQueryTooShort = true
            return@LaunchedEffect
        }
        val response = autocomplete.suggestions(input)
        response.onValue { querySuggestions ->
            if (querySuggestions.isEmpty()) {
                error = "Location not found"
                return@onValue
            }
            launch {
                suggestions = emptyMap()
                querySuggestions.forEach {
                    val address = autocomplete.select(it)
                    if (address.value != null && address.value?.coordinate != null && address.value?.address?.region != null) {
                        val coordinate = address.value?.coordinate
                        if (coordinate != null) {
                            suggestions =
                                suggestions + (address.value?.address!! to coordinate)
                        }
                    }
                }
            }
            isQueryTooShort = false
            error = null
        }.onError {
            error = it.message
        }
    }

    AddressDropdown(
        modifier = modifier,
        label = label,
        input = input,
        items = suggestions,
        onInputChange = { input = it },
        onSelectedChange = {
            input = "${it.key.place}, ${it.key.region}"
            setLocation(
                LocationDto(
                    it.key.place!!,
                    it.key.region!!,
                    it.value.latitude(),
                    it.value.longitude()
                ),
                it.key.region!!
            )
        }
    )
}