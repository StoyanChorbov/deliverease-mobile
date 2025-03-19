package xyz.deliverease.deliverease.user.register

data class RegisterState(
    val username: String = "",
    val isUsernameValid: Boolean = true,
    val email: String = "",
    val isEmailValid: Boolean = true,
    val firstName: String = "",
    val isFirstNameValid: Boolean = true,
    val lastName: String = "",
    val isLastNameValid: Boolean = true,
    val phoneNumber: String = "",
    val isPhoneNumberValid: Boolean = true,
    val password: String = "",
    val isPasswordValid: Boolean = true,
    val confirmPassword: String = "",
    val passwordsMatch: Boolean = true,
    val isInputValid: Boolean = true,
    val error: String? = null,
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
)
