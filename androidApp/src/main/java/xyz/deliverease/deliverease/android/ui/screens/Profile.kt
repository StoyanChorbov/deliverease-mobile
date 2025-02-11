package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import xyz.deliverease.deliverease.UserService

//TODO("Move to shared module as dto")
data class Profile(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String
)

suspend fun getProfile(): Profile {
    val userDTO = UserService().login()
    return Profile(userDTO.firstName, userDTO.lastName, userDTO.username, userDTO.email)
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    var profile by remember { mutableStateOf<Profile?>(null) }

    LaunchedEffect(Unit) {
        profile = getProfile()
    }

    profile?.let{
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