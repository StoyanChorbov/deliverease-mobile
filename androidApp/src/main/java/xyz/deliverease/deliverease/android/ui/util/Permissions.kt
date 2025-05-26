package xyz.deliverease.deliverease.android.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun RequestPermission(
    permission: String,
    rationaleMessage: String,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val permissionsLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    onPermissionGranted()
                } else {
                    onPermissionDenied()
                }
            }
        )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if (!isPermissionGranted(context, permission)) {
                    permissionsLauncher.launch(permission)
                } else {
                    onPermissionGranted()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

//@Composable
//fun RequestBackgroundLocation(
//    onPermissionGranted: () -> Unit,
//    onPermissionDenied: () -> Unit
//) {
//    val context = LocalContext.current
//    val permissionsLauncher =
//        rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.RequestPermission(),
//            onResult = { isGranted ->
//                if (isGranted) {
//                    onPermissionGranted()
//                } else {
//                    onPermissionDenied()
//                }
//            }
//        )
//
//    DisposableEffect(Unit) {
//        if (isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            if (!isPermissionGranted(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
//                permissionsLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//            } else {
//                onPermissionGranted()
//            }
//        } else {
//            onPermissionDenied()
//        }
//        onDispose { }
//    }
//}


fun isPermissionGranted(context: Context, permission: String): Boolean =
    ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED