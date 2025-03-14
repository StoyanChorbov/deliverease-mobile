package xyz.deliverease.deliverease.android.ui.display

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.DisposableMapEffect
import com.mapbox.maps.extension.compose.MapState
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.toCameraOptions
import com.mapbox.search.autofill.AddressAutofill
import com.mapbox.search.autofill.AddressAutofillOptions
import com.mapbox.search.autofill.AddressAutofillSuggestion
import com.mapbox.search.autofill.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.deliverease.deliverease.android.ui.input.TextInputField

@Composable
fun TestMap(modifier: Modifier = Modifier) {
//    val addressAutofill = AddressAutofill.create()
//    var input by remember { mutableStateOf("24.627979,42.315073") }
//    var isQueryTooShort by remember { mutableStateOf(false) }
//    val suggestions = remember { mutableStateListOf<AddressAutofillSuggestion>() }

    val context = LocalContext.current
    val cameraState = CameraState(
        Point.fromLngLat(24.627979, 42.315073),
        EdgeInsets(0.0, 0.0, 0.0, 0.0),
        11.0,
        0.0,
        0.0
    )
    val isDarkTheme = isSystemInDarkTheme()

    val mapView = remember {
        MapView(context).apply {
            mapboxMap
                .loadStyle(if (isDarkTheme) Style.DARK else Style.LIGHT)
            mapboxMap
                .setCamera(cameraOptions = cameraState.toCameraOptions())
        }
    }


//    LaunchedEffect(input) {
//        if (input.length < 2) {
//            isQueryTooShort = true
//            return@LaunchedEffect
//        }
//        launch(Dispatchers.IO) {
//            val query = Query.create(input)
//            if (query != null) {
//                val response = addressAutofill.suggestions(query, AddressAutofillOptions())
//                response.onValue {
//                    Log.d("TEST", it.toString())
//                    suggestions.clear()
//                    suggestions.addAll(it)
//                }.onError {
//                    TODO("Handle error")
//                }
//            }
//        }
//    }

    Column(modifier = modifier) {
        AndroidView(factory = { mapView }, modifier = Modifier)
    }
}