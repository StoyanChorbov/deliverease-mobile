package xyz.deliverease.deliverease.android.ui.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.user.register.UserRegisterDTO
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.input.PasswordInputField
import xyz.deliverease.deliverease.android.ui.input.TextInputField
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.user.register.RegisterViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = koinViewModel()
) {
    val navController = LocalNavController.current
    val registerState by registerViewModel.registerState.collectAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInputField(
            label = "Username",
            value = registerState.username,
            isError = !registerState.isUsernameValid,
            onChange = { registerViewModel.setUsername(it) }
        )
        TextInputField(
            label = "Email",
            value = registerState.email,
            isError = !registerState.isEmailValid,
            onChange = { registerViewModel.setEmail(it) }
        )
        TextInputField(
            label = "First name",
            value = registerState.firstName,
            isError = !registerState.isFirstNameValid,
            onChange = { registerViewModel.setFirstName(it) }
        )
        TextInputField(
            label = "Last name",
            value = registerState.lastName,
            isError = !registerState.isLastNameValid,
            onChange = { registerViewModel.setLastName(it) }
        )
        TextInputField(
            label = "Phone number(optional)",
            value = registerState.phoneNumber,
            isError = !registerState.isPhoneNumberValid,
            onChange = { registerViewModel.setPhoneNumber(it) }
        )
        PasswordInputField(
            label = "Password",
            value = registerState.password,
            isError = !(registerState.isPasswordValid && registerState.passwordsMatch),
            onChange = { registerViewModel.setPassword(it) },
            isPasswordVisible = isPasswordVisible,
            changePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        PasswordInputField(
            label = "Confirm Password",
            value = registerState.confirmPassword,
            isError = !registerState.passwordsMatch,
            onChange = { registerViewModel.setConfirmPassword(it) },
            isPasswordVisible = isPasswordVisible,
            changePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", fontSize = 16.sp)
            TextButton(
                onClick = { navigateTo(navController, NavDestination.Login.route) },
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
                onClick = {
                    registerViewModel.register()

                    val updatedState = registerViewModel.registerState.value

                    if (updatedState.isInputValid && !updatedState.hasError) {
                        navigateTo(navController = navController, NavDestination.Login.route)
                    }
                },
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