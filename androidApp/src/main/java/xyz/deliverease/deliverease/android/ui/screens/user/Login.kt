package xyz.deliverease.deliverease.android.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import xyz.deliverease.deliverease.android.ui.display.Loading
import xyz.deliverease.deliverease.android.ui.input.PasswordInputField
import xyz.deliverease.deliverease.android.ui.input.TextInputField
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.user.login.LoginEvent
import xyz.deliverease.deliverease.user.login.LoginState
import xyz.deliverease.deliverease.user.login.LoginViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = koinViewModel(),
) {
    val navController = LocalNavController.current
    val loginState by loginViewModel.loginState.collectAsState()
    val loginEvent by loginViewModel.loginEvent.collectAsState(initial = LoginEvent.Idle)

    LaunchedEffect(loginEvent) {
        when(loginEvent) {
            is LoginEvent.Navigate.Home -> navigateTo(navController = navController, NavDestination.Home.route)
            else -> {}
        }
    }

    LoginScreen(
        modifier = modifier,
        loginState = loginState,
        setUsername = { loginViewModel.onEvent(LoginEvent.Input.EnterUsername(it)) },
        setPassword = { loginViewModel.onEvent(LoginEvent.Input.EnterPassword(it)) },
        navigateToRegister = { navigateTo(navController = navController, NavDestination.Register.route) },
        onLogin = {
            loginViewModel.login()
        }
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    setUsername: (String) -> Unit,
    setPassword: (String) -> Unit,
    navigateToRegister: () -> Unit,
    onLogin: () -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInputField(
            label = "Username",
            value = loginState.username,
            isError = !loginState.usernameErrorMessage.isNullOrBlank(),
            onChange = setUsername,
        )
        loginState.usernameErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        PasswordInputField(
            label = "Password",
            value = loginState.password,
            isError = !loginState.passwordErrorMessage.isNullOrBlank(),
            onChange = setPassword,
            isPasswordVisible = isPasswordVisible,
            changePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        loginState.passwordErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?", fontSize = 16.sp)
            TextButton(
                onClick = navigateToRegister,
                modifier = Modifier
                    .padding(0.dp)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Register here.",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(0.dp),
                    fontSize = 16.sp
                )
            }
        }
        if (loginState.isLoading) {
            Loading()
        } else {
            ElevatedButton(
                onClick = onLogin,
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