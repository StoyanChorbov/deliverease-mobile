package xyz.deliverease.deliverease.android.ui.location

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.checkCoarseLocationPermission(): Boolean {
    // Check if the app has the ACCESS_COARSE_LOCATION permission
    val result = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun Context.checkFineLocationPermission(): Boolean {
    // Check if the app has the ACCESS_COARSE_LOCATION permission
    val result = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    return result == PackageManager.PERMISSION_GRANTED
}

//fun checkBackgroundLocationPermission(context: Context): Boolean {
//    // Check if the app has the ACCESS_COARSE_LOCATION permission
//    val result = ContextCompat.checkSelfPermission(
//        context,
//        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
//    )
//    return result == PackageManager.PERMISSION_GRANTED
//}

fun Context.checkLocationPermissions(): Boolean {
    return this.checkCoarseLocationPermission()
            && this.checkFineLocationPermission()
//            && checkBackgroundLocationPermission(context)
}