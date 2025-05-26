package xyz.deliverease.deliverease.android.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> = callbackFlow {
        if (!context.checkLocationPermissions()) {
            throw LocationClient.LocationException("Missing location permissions")
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled) {
            throw LocationClient.LocationException("GPS is disabled")
        }

        if (!isNetworkEnabled) {
            throw LocationClient.LocationException("No network connection")
        }

        val request = LocationRequest.Builder(interval).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.lastLocation.let { location ->
                    if (location != null) {
                        launch { send(location) }
                    }
                }
            }
        }

        client.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            client.removeLocationUpdates(locationCallback)
        }
    }

    @SuppressLint("MissingPermission")
    override fun getLastLocation(): Task<Location> {
        if (!context.checkLocationPermissions()) {
            throw LocationClient.LocationException("Missing location permissions")
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled) {
            throw LocationClient.LocationException("GPS is disabled")
        }

        if (!isNetworkEnabled) {
            throw LocationClient.LocationException("No network connection")
        }

        return client.lastLocation
    }
}