package xyz.deliverease.deliverease.user.register

data class RegisterState(
    val username: String = "",
    val usernameErrorMessage: String? = null,
    val email: String = "",
    val emailErrorMessage: String? = null,
    val firstName: String = "",
    val firstNameErrorMessage: String? = null,
    val lastName: String = "",
    val lastNameErrorMessage: String? = null,
    val phoneNumber: String = "",
    val phoneNumberErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordErrorMessage: String? = null,
    val areTermsAndConditionsAccepted: Boolean = false,
    val termsAndConditionsErrorMessage: String? = null,
    val isInputValid: Boolean = true,
    val error: String? = null,
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
)
