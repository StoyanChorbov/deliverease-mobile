package xyz.deliverease.deliverease.android.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import xyz.deliverease.deliverease.android.ui.components.input.PasswordInputField
import xyz.deliverease.deliverease.android.ui.components.input.TextInputField
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.user.register.RegisterEvent
import xyz.deliverease.deliverease.user.register.RegisterState
import xyz.deliverease.deliverease.user.register.RegisterViewModel

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = koinViewModel(),
) {
    val navController = LocalNavController.current
    val registerState by registerViewModel.registerState.collectAsState()
    val registerEvent by registerViewModel.registerEvent.collectAsState(initial = RegisterEvent.Idle)

    LaunchedEffect(registerEvent) {
        when (registerEvent) {
            is RegisterEvent.Navigate.Login -> navigateTo(
                navController = navController,
                NavDestination.Login.route
            )
            else -> {}
        }
    }

    RegisterScreen(
        modifier = modifier,
        registerState = registerState,
        setUsername = { registerViewModel.onEvent(RegisterEvent.Input.EnterUsername(it)) },
        setEmail = { registerViewModel.onEvent(RegisterEvent.Input.EnterEmail(it)) },
        setFirstName = { registerViewModel.onEvent(RegisterEvent.Input.EnterFirstName(it)) },
        setLastName = { registerViewModel.onEvent(RegisterEvent.Input.EnterLastName(it)) },
        setPhoneNumber = { registerViewModel.onEvent(RegisterEvent.Input.EnterPhoneNumber(it)) },
        setPassword = { registerViewModel.onEvent(RegisterEvent.Input.EnterPassword(it)) },
        setConfirmPassword = { registerViewModel.onEvent(RegisterEvent.Input.EnterConfirmPassword(it)) },
        navigateToLogin = { navigateTo(navController = navController, NavDestination.Login.route) },
        onRegister = { registerViewModel.register() }
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerState: RegisterState,
    setUsername: (String) -> Unit,
    setEmail: (String) -> Unit,
    setFirstName: (String) -> Unit,
    setLastName: (String) -> Unit,
    setPhoneNumber: (String) -> Unit,
    setPassword: (String) -> Unit,
    setConfirmPassword: (String) -> Unit,
    navigateToLogin: () -> Unit,
    onRegister: () -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInputField(
            label = "Username",
            value = registerState.username,
            isError = registerState.usernameErrorMessage != null,
            onChange = { setUsername(it) }
        )
        registerState.usernameErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        TextInputField(
            label = "Email",
            value = registerState.email,
            isError = registerState.emailErrorMessage != null,
            onChange = { setEmail(it) }
        )
        registerState.emailErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        TextInputField(
            label = "First name",
            value = registerState.firstName,
            isError = registerState.firstNameErrorMessage != null,
            onChange = { setFirstName(it) }
        )
        registerState.firstNameErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        TextInputField(
            label = "Last name",
            value = registerState.lastName,
            isError = registerState.lastNameErrorMessage != null,
            onChange = { setLastName(it) }
        )
        registerState.lastNameErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        TextInputField(
            label = "Phone number(optional)",
            value = registerState.phoneNumber,
            isError = registerState.phoneNumberErrorMessage != null,
            onChange = { setPhoneNumber(it) }
        )
        registerState.phoneNumberErrorMessage.let {
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
            value = registerState.password,
            isError = registerState.passwordErrorMessage != null,
            onChange = { setPassword(it) },
            isPasswordVisible = isPasswordVisible,
            changePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        registerState.passwordErrorMessage.let {
            if (it != null) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        }
        PasswordInputField(
            label = "Confirm Password",
            value = registerState.confirmPassword,
            isError = registerState.confirmPasswordErrorMessage != null,
            onChange = { setConfirmPassword(it) },
            isPasswordVisible = isPasswordVisible,
            changePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        registerState.confirmPasswordErrorMessage.let {
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
            Text(text = "Already have an account?", fontSize = 16.sp)
            TextButton(
                onClick = navigateToLogin,
                modifier = Modifier
                    .padding(0.dp)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Log in here.",
                    modifier = Modifier.padding(0.dp),
                    fontSize = 16.sp
                )
            }
        }
        if (registerState.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(24.dp)
            )
        } else {
            ElevatedButton(
                onClick = onRegister,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            ) {
                Text("Register")
            }
        }
    }
}