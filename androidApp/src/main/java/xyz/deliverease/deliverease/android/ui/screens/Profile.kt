package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.ui.display.Loading
import xyz.deliverease.deliverease.user.profile.ProfileViewModel

//TODO("Move to shared module as dto")
data class Profile(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String
)

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, profileViewModel: ProfileViewModel = koinViewModel()) {
    val profileState by profileViewModel.profileState.collectAsState()

    if (profileState.loading) {
        Loading()
    } else if (profileState.error != null) {
        val error = profileState.error
        // TODO("Handle error")
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
            }
        }
    }

}