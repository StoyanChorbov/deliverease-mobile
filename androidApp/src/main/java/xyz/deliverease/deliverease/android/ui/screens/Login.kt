package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.input.PasswordInputField
import xyz.deliverease.deliverease.android.ui.input.TextInputField
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.user.login.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val navController = LocalNavController.current
    val state by loginViewModel.loginState.collectAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInputField(
            label = "Username",
            value = state.username,
            isError = !state.isUsernameValid,
            onChange = { loginViewModel.setUsername(it) }
        )
        PasswordInputField(
            label = "Password",
            value = state.password,
            isError = !state.isPasswordValid,
            onChange = { loginViewModel.setPassword(it) },
            isPasswordVisible = isPasswordVisible,
            changePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?", fontSize = 16.sp)
            TextButton(
                onClick = { navigateTo(navController = navController, NavDestination.Register.route) },
                modifier = Modifier
                    .padding(0.dp)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Register here.",
                    color = if (state.isInputValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(0.dp),
                    fontSize = 16.sp
                )
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            ElevatedButton(
                onClick = {
                    loginViewModel.login()

                    val updatedState = loginViewModel.loginState.value

                    if (updatedState.isInputValid && !updatedState.hasError) {
                        navigateTo(navController = navController, NavDestination.Home.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            ) {
                Text("Log in")
            }
        }
    }
}