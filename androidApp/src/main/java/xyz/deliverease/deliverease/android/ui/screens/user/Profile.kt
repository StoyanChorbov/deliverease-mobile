package xyz.deliverease.deliverease.android.ui.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.components.display.LoadingIndicator
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.user.profile.ProfileEvent
import xyz.deliverease.deliverease.user.profile.ProfileState
import xyz.deliverease.deliverease.user.profile.ProfileViewModel

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsState()
    val profileEvent by profileViewModel.profileEvent.collectAsState(initial = ProfileEvent.Idle)
    val navController = LocalNavController.current

    LaunchedEffect(profileEvent) {
        when (profileEvent) {
            is ProfileEvent.Redirect -> navigateTo(
                navController = navController,
                route = NavDestination.Login.route
            )
            else -> {}
        }
    }

    ProfileScreen(modifier = modifier, profileState = profileState, handleLogout = {
        profileViewModel.onEvent(ProfileEvent.Logout)
    })
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileState: ProfileState,
    handleLogout: () -> Unit
) {
    val error = profileState.error

    if (profileState.loading) {
        LoadingIndicator()
    } else if (error != null) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    } else {
        profileState.profile.let {
            Column(modifier = modifier.padding(16.dp)) {
                Text(
                    text = "${it.firstName} ${it.lastName}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = it.username,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )
                OutlinedButton(
                    onClick = handleLogout,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.secondary)
                TextButton(
                    onClick = {}
                ) {
                    Text("Sent packages")
                }
            }
        }
    }

}