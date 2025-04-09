package xyz.deliverease.deliverease.android.ui.display

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.search.autofill.AddressAutofill
import com.mapbox.search.autofill.AddressAutofillOptions
import com.mapbox.search.autofill.AddressAutofillSuggestion
import com.mapbox.search.autofill.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.android.ui.input.DropdownWithLabel
import xyz.deliverease.deliverease.delivery.Location

@Composable
fun AutofillWithDropdown(modifier: Modifier = Modifier, label: String, items: List<String>, onChange: (String) -> Unit) {
    Column(modifier = modifier) {
        DropdownWithLabel(
            label = label,
            items = items,
            onSelectedChange = onChange
        )
    }
}

@Composable
fun LocationAutofill(
    modifier: Modifier = Modifier,
    label: String,
    setLocation: (Location) -> Unit
) {
    val addressAutofill = AddressAutofill.create()
    var input by remember { mutableStateOf("") }
    var isQueryTooShort by remember { mutableStateOf(false) }
    val locationOptions = remember { mutableStateListOf<AddressAutofillSuggestion>() }

    LaunchedEffect(input) {
        if (input.length < 2) {
            isQueryTooShort = true
            return@LaunchedEffect
        }
        launch(Dispatchers.IO) {
            val query = Query.create(input)
            if (query != null) {
                val response = addressAutofill.suggestions(query, AddressAutofillOptions())
                response.onValue {
                    isQueryTooShort = false
                    locationOptions.clear()
                    locationOptions.addAll(it)
                }.onError {
                    TODO("Handle error")
                }
            }
        }
    }

    AutofillWithDropdown(
        modifier = modifier,
        label = label,
        items = (1..10).map { it.toString() },
//        items = locationOptions.map { it.name }.subList(0, 8),
        onChange = {
            input = it
            val selectedLocation =
                locationOptions.find { suggestion -> suggestion.name == it }
            if (selectedLocation == null) {
                TODO("Handle error")
            }
            setLocation(
                Location(
                    selectedLocation.name,
                    selectedLocation.coordinate?.latitude() ?: 0.0,
                    selectedLocation.coordinate?.longitude() ?: 0.0
                )
            )
        }
    )
}