package xyz.deliverease.deliverease.android.ui.location

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

@SuppressLint("RememberReturnType", "MissingPermission")
@Composable
fun currentLocation(): Location? {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    DisposableEffect(Unit) {
        val task: Task<Location> = fusedLocationClient.lastLocation
        task.addOnSuccessListener { result ->
            location = result
        }
        onDispose {}
    }

    return location
}