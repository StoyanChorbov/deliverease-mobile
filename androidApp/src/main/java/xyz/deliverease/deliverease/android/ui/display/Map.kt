package xyz.deliverease.deliverease.android.ui.display

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.search.autofill.AddressAutofill
import com.mapbox.search.autofill.AddressAutofillOptions
import com.mapbox.search.autofill.AddressAutofillSuggestion
import com.mapbox.search.autofill.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.android.ui.input.TextInputField

@Composable
fun TestMap(modifier: Modifier = Modifier) {
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(11.0)
            center(Point.fromLngLat(24.627979, 42.315073))
            bearing(0.0)
            pitch(0.0)
        }
    }
    val isDarkTheme = isSystemInDarkTheme()
    MapboxMap(
        modifier = modifier.fillMaxWidth().fillMaxHeight(0.5f),
        mapViewportState = mapViewportState,
        style = { if (isDarkTheme) MapStyle(Style.DARK) else MapStyle(Style.LIGHT) }
    )
}

@Composable
fun AutofillTest(modifier: Modifier = Modifier) {
    val addressAutofill = AddressAutofill.create()
    var input by remember { mutableStateOf("24.627979,42.315073") }
    var isQueryTooShort by remember { mutableStateOf(false) }
    val suggestions = remember { mutableStateListOf<AddressAutofillSuggestion>() }

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
                    Log.d("TEST", it.toString())
                    suggestions.clear()
                    suggestions.addAll(it)
                }.onError {
                    TODO("Handle error")
                }
            }
        }
    }

    Column(modifier = modifier) {
        TextInputField(
            label = "Destination",
            value = input,
            onChange = { input = it }
        )
        suggestions.forEach {
            Text(it.name)
        }
    }

}