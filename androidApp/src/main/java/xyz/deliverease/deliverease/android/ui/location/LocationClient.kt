package xyz.deliverease.deliverease.android.ui.location

import android.location.Location
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>
    fun getLastLocation(): Task<Location>

    class LocationException(message: String) : Exception(message)
}