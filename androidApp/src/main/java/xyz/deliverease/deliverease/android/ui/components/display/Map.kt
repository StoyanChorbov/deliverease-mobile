package xyz.deliverease.deliverease.android.ui.components.display

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.style.MapStyle
import xyz.deliverease.deliverease.android.R
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.delivery.find.DeliveryMarkerDetails

@Composable
fun BasicMap(modifier: Modifier = Modifier) {
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
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        mapViewportState = mapViewportState,
        style = { if (isDarkTheme) MapStyle(Style.DARK) else MapStyle(Style.LIGHT) }
    )
}

@Composable
fun MapWithMarkers(modifier: Modifier = Modifier, points: Set<LocationDto>) {
    val markers = points.map { Point.fromLngLat(it.longitude, it.latitude) }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(11.0)
            center(if (markers.isNotEmpty()) markers[0] else Point.fromLngLat(0.0, 0.0))
            bearing(0.0)
            pitch(0.0)
        }
    }
    val isDarkTheme = isSystemInDarkTheme()
    MapboxMap(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        mapViewportState = mapViewportState,
        style = { if (isDarkTheme) MapStyle(Style.DARK) else MapStyle(Style.LIGHT) }
    ) {
        val marker =
            rememberIconImage(key = "marker", painter = painterResource(R.drawable.red_marker))

        markers.forEach {
            PointAnnotation(point = it) {
                iconImage = marker
            }
        }
    }
}

@Composable
fun MapWithLiveLocationAndMarkers(
    modifier: Modifier = Modifier,
    points: Set<LocationDto>,
    liveLocationDto: LocationDto?
) {
    val markers = points.map { Point.fromLngLat(it.longitude, it.latitude) }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(3.0)
            center(if (markers.isNotEmpty()) markers[0] else Point.fromLngLat(0.0, 0.0))
            bearing(0.0)
            pitch(0.0)
        }
    }
    val isDarkTheme = isSystemInDarkTheme()
    MapboxMap(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        mapViewportState = mapViewportState,
        style = { if (isDarkTheme) MapStyle(Style.DARK) else MapStyle(Style.LIGHT) }
    ) {
        val pointMarker =
            rememberIconImage(key = "marker", painter = painterResource(R.drawable.red_marker))

        val liveLocationMarker =
            rememberIconImage(
                key = "live_location",
                painter = painterResource(R.drawable.red_marker)
            )

        markers.forEach {
            PointAnnotation(point = it) {
                iconImage = pointMarker
            }
        }
        if (liveLocationDto != null) {
            PointAnnotation(
                point = Point.fromLngLat(liveLocationDto.longitude, liveLocationDto.latitude),
            ) {
                iconImage = liveLocationMarker
                iconColor = Color(0xFF00FF00) // Green color for live location
                textField = "Wassup"
                textColor = Color(0xFF00FF00)
            }
        }
    }
}

@Composable
fun DeliveriesMapWithClickableMarkers(
    modifier: Modifier = Modifier,
    points: Set<DeliveryMarkerDetails>
) {
    val markers = points.map {
        Pair(
            Point.fromLngLat(
                it.startingLocationDto.longitude,
                it.startingLocationDto.latitude
            ),
            Point.fromLngLat(
                it.endingLocationDto.longitude,
                it.endingLocationDto.latitude
            )
        )
    }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(11.0)
            center(Point.fromLngLat(0.0, 0.0))
            bearing(0.0)
            pitch(0.0)
        }
    }
    val isDarkTheme = isSystemInDarkTheme()
    MapboxMap(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        mapViewportState = mapViewportState,
        style = { if (isDarkTheme) MapStyle(Style.DARK) else MapStyle(Style.LIGHT) }
    ) {
        val marker =
            rememberIconImage(key = "marker", painter = painterResource(R.drawable.red_marker))

        markers.forEach {
            PointAnnotation(point = it.first) {
                iconImage = marker
            }
        }
    }
}

@Composable
fun MapWithPath(modifier: Modifier = Modifier, start: Point, end: Point) {

}