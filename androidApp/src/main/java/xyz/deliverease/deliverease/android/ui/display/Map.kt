//package eu.deliverease.deliverease.android.ui.display
//
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.mapbox.geojson.Point
//import com.mapbox.maps.extension.compose.MapboxMap
//import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
//
//@Composable
//fun TestMap(modifier: Modifier = Modifier) {
//    val mapViewportState = rememberMapViewportState {
//        setCameraOptions {
//            zoom(11.0)
//            center(Point.fromLngLat(24.751568980035962, 42.14758863740026))
//            pitch(0.0)
//            bearing(0.0)
//        }
//    }
//    MapboxMap(
//        modifier = modifier.fillMaxWidth().fillMaxHeight(0.75f),
//        mapViewportState = mapViewportState,
//    )
//}