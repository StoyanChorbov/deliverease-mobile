package xyz.deliverease.deliverease.user.login

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.util.validation.ValidatePassword
import xyz.deliverease.deliverease.util.validation.ValidateUsername

class LoginViewModel(
    private val userRepository: UserRepository,
    private val usernameValidator: ValidateUsername,
    private val passwordValidator: ValidatePassword
) : BaseViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = LoginState()
    )

    private val _loginEvent: Channel<LoginEvent> = Channel()
    val loginEvent = _loginEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Input.EnterUsername -> {
                _loginState.update {
                    it.copy(username = event.value)
                }
            }

            is LoginEvent.Input.EnterPassword -> {
                _loginState.update {
                    it.copy(password = event.value)
                }
            }

            is LoginEvent.Submit -> login()
            else -> {}
        }
    }

    fun login() {
        scope.launch {
            _loginState.update {
                it.copy(isLoading = true)
            }

            validateFields()

            // TODO: Add handling of parameter exceptions, server errors and validation errors...

            val currentState = _loginState.value

            if (currentState.isInputValid) {
                try {
                    val userDto = currentState.run {
                        userRepository.login(
                            UserLoginDTO(
                                username = username,
                                password = password
                            )
                        )
                    }
                    // TODO("Save user and token in storage for auto login")
                    _loginState.update {
                        it.copy(
                            isLoggedIn = true,
                            error = null,
                            hasError = false,
                            isLoading = false
                        )
                    }
                    _loginEvent.send(LoginEvent.Navigate.Home)
                } catch (e: Exception) {
                    _loginState.update { it.copy(error = e.message, hasError = true) }
                    _loginEvent.send(LoginEvent.Error(e.message ?: "An error occurred"))
                }
            }
        }
    }

    fun setPassword(password: String) {
        _loginState.update {
            it.copy(password = password)
        }
    }

    private fun validateFields() {
        val usernameResult = usernameValidator.validate(_loginState.value.username)
        val passwordResult = passwordValidator.validate(_loginState.value.password)
        if (!usernameResult.success || !passwordResult.success) {
            _loginState.update {
                it.copy(
                    usernameErrorMessage = usernameResult.error,
                    passwordErrorMessage = passwordResult.error,
                    isInputValid = false,
                    isLoading = false
                )
            }
        }
    }
}