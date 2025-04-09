package xyz.deliverease.deliverease.user.login

data class LoginState(
    val username: String = "",
    val usernameErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val isInputValid: Boolean = true,
    val error: String? = null,
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)
