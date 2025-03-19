package xyz.deliverease.deliverease.user.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isUsernameValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isInputValid: Boolean = true,
    val error: String? = null,
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
)
