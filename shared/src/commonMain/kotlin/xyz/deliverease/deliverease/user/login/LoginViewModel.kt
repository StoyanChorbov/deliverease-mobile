package xyz.deliverease.deliverease.user.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    init {
        getInitialState()
    }

    private fun getInitialState() {
        _loginState.update { LoginState() }
    }

    fun login() {
        scope.launch {
            _loginState.update {
                it.copy(isLoading = true)
            }

            validateFields()

            // TODO: Add handling of parameter exceptions, server errors and validation errors...
            if (_loginState.value.isInputValid) {
                try {
                    val userDto = _loginState.value.run { userRepository.login(
                        UserLoginDTO(
                            username = username,
                            password = password
                        )
                    ) }
                    _loginState.update {
                        it.copy()
                    }
                    // TODO("Save user and token in storage for auto login")
                } catch (e: Exception) {
                    _loginState.update { it.copy(error = e.message, hasError = true) }
                }
            }
        }
    }

    private fun validateFields() {
        val isUsernameValid = validateUsername()
        val isPasswordValid = validatePassword()
        _loginState.update {
            it.copy(
                isUsernameValid = isUsernameValid,
                isPasswordValid = isPasswordValid,
                isInputValid = isUsernameValid && isPasswordValid,
                isLoading = false
            )
        }
    }

    private fun validateUsername(): Boolean {
        if (_loginState.value.username.isEmpty()) {
            return false
        }

        return true
    }

    private fun validatePassword(): Boolean {
        if (_loginState.value.password.isEmpty()) {
            return false
        }

        return true
    }

    fun setUsername(username: String) {
        _loginState.update {
            it.copy(username = username)
        }
    }

    fun setPassword(password: String) {
        _loginState.update {
            it.copy(password = password)
        }
    }
}