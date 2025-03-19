package xyz.deliverease.deliverease.user.register

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    init {
        getInitialState()
    }

    private fun getInitialState() {
        _registerState.update { RegisterState() }
    }

    fun register() {
        scope.launch {
            _registerState.update {
                it.copy(isLoading = true)
            }

            validateFields()

            // TODO: Add handling of parameter exceptions, server errors and validation errors...
            if (_registerState.value.isInputValid) {
                try {
                    val userDto = _registerState.value.run {
                        userRepository.register(
                            UserRegisterDTO(
                                username = username,
                                password = password,
                                confirmPassword = confirmPassword,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                phoneNumber = phoneNumber
                            )
                        )
                    }
                    _registerState.update {
                        it.copy()
                    }
                    // TODO("Save user and token in storage for auto login")
                } catch (e: Exception) {
                    _registerState.update { it.copy(error = e.message, hasError = true) }
                }
            }
        }
    }

    private fun validateFields() {
        val currentState = _registerState.value
        val isUsernameValid = validateUsername(currentState.username)
        val isEmailValid = validateEmail(currentState.email)
        val isFirstNameValid = validateName(currentState.firstName)
        val isLastNameValid = validateName(currentState.lastName)
        val isPhoneNumberValid = validatePhoneNumber(currentState.phoneNumber)
        val isPasswordValid = validatePassword(currentState.password)
        val passwordsMatch = currentState.password == currentState.confirmPassword

        _registerState.update {
            it.copy(
                isUsernameValid = isUsernameValid,
                isEmailValid = isEmailValid,
                isFirstNameValid = isFirstNameValid,
                isLastNameValid = isLastNameValid,
                isPhoneNumberValid = isPhoneNumberValid,
                isPasswordValid = isPasswordValid,
                passwordsMatch = passwordsMatch,
                isInputValid = isUsernameValid && isEmailValid && isFirstNameValid && isLastNameValid && isPhoneNumberValid && isPasswordValid && passwordsMatch,
                isLoading = false
            )
        }
    }

    private fun validateUsername(username: String): Boolean {
        if (username.isEmpty()) {
            return false
        }

        return true
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }

        return true
    }

    // Validation for either first or last name
    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            return false
        }

        return true
    }

    private fun validatePhoneNumber(number: String): Boolean {
        if (number.isEmpty()) {
            return false
        }

        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            return false
        }

        return true
    }

    fun setUsername(username: String) {
        _registerState.update {
            it.copy(username = username)
        }
    }

    fun setEmail(email: String) {
        _registerState.update {
            it.copy(email = email)
        }
    }

    fun setFirstName(firstName: String) {
        _registerState.update {
            it.copy(firstName = firstName)
        }
    }

    fun setLastName(firstName: String) {
        _registerState.update {
            it.copy(lastName = firstName)
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        _registerState.update {
            it.copy(phoneNumber = phoneNumber)
        }
    }

    fun setPassword(password: String) {
        _registerState.update {
            it.copy(password = password)
        }
    }

    fun setConfirmPassword(confirmPassword: String) {
        _registerState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }
}