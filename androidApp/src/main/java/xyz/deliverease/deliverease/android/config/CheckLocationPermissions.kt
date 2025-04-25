package xyz.deliverease.deliverease.android.config

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun checkCoarseLocationPermission(context: Context): Boolean {
    // Check if the app has the ACCESS_COARSE_LOCATION permission
    val result = ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun checkFineLocationPermission(context: Context): Boolean {
    // Check if the app has the ACCESS_COARSE_LOCATION permission
    val result = ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun checkBackgroundLocationPermission(context: Context): Boolean {
    // Check if the app has the ACCESS_COARSE_LOCATION permission
    val result = ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun checkLocationPermissions(context: Context): Boolean {
    return checkCoarseLocationPermission(context) &&
            checkFineLocationPermission(context) &&
            checkBackgroundLocationPermission(context)
}