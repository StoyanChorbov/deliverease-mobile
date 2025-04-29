package xyz.deliverease.deliverease.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mapbox.common.MapboxOptions
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin
import xyz.deliverease.deliverease.android.config.androidAppModule
import xyz.deliverease.deliverease.android.ui.components.display.LoadingIndicator
import xyz.deliverease.deliverease.android.ui.navigation.NavBar
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.android.ui.navigation.NavGraph
import xyz.deliverease.deliverease.android.ui.theme.DelivereaseTheme
import xyz.deliverease.deliverease.main.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapboxOptions.accessToken = getString(R.string.mapbox_access_token)

        startKoin {
            androidContext(this@MainActivity)
            modules(androidAppModule)
        }

//        MapboxNavigationApp.setup {
//            NavigationOptions
//                .Builder(this)
//                .build()
//        }

        setContent {
            DelivereaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

val LocalNavController =
    staticCompositionLocalOf<NavController> {
        error("No NavController provided")
    }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(mainViewModel: MainViewModel = koinViewModel()) {
    val state by mainViewModel.mainState.collectAsState()
//    val isLoggedIn = state.isLoggedIn
    val isLoggedIn = true
    val navController = rememberNavController()
    val activity = LocalActivity.current ?: throw IllegalStateException("Activity is null")

    var showRationale by remember { mutableStateOf(false) }
    var showSettingDialogue by remember { mutableStateOf(false) }

    RequestPermission(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
        rationaleMessage = "This app requires location permissions to find and track deliveries.",
        onPermissionGranted = { Log.d("PermissionCallback", "Coarse location access permission granted!") },
        onPermissionDenied = { showRationale = true }
    )

    RequestPermission(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        rationaleMessage = "This app requires location permissions to find and track deliveries.",
        onPermissionGranted = { Log.d("PermissionCallback", "Fine location access permission granted!") },
        onPermissionDenied = { showRationale = true }
    )

    RequestBackgroundLocation(
        onPermissionGranted = { Log.d("PermissionCallback", "Background location access permission granted!") },
        onPermissionDenied = { showRationale = true }
    )

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showSettingDialogue = true },
            title = { Text("Location Permissions Required") },
            text = {
                Text("This app requires location permissions to function properly.")
            },
            confirmButton = {
                TextButton(onClick = {
                    activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity.packageName, null)
                    })
                    showRationale = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {}) {
                    Text("Cancel")
                }
            }
        )
    }

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            bottomBar = {
                Log.d("We got here", "Like wut")
                if (isLoggedIn) NavBar()
            }
        ) {
            if (state.loading)
                LoadingIndicator()
            else {
                NavGraph(
                    navController = navController,
                    startDestination = if (isLoggedIn) NavDestination.Home else NavDestination.Login
                )
            }
        }
    }
}

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

@Composable
fun RequestBackgroundLocation(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
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

    DisposableEffect(Unit) {
        if (isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (!isPermissionGranted(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                permissionsLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            } else {
                onPermissionGranted()
            }
        } else {
            onPermissionDenied()
        }
        onDispose { }
    }
}

fun navigateTo(navController: NavController, route: String) =
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun isPermissionGranted(context: Context, permission: String): Boolean =
    ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED