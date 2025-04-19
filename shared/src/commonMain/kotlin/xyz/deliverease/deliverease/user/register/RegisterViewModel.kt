package xyz.deliverease.deliverease.user.register

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.util.validation.ValidateEmail
import xyz.deliverease.deliverease.util.validation.ValidateName
import xyz.deliverease.deliverease.util.validation.ValidatePassword
import xyz.deliverease.deliverease.util.validation.ValidatePhoneNumber
import xyz.deliverease.deliverease.util.validation.ValidateTermsAndConditions
import xyz.deliverease.deliverease.util.validation.ValidateUsername

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val usernameValidator: ValidateUsername,
    private val passwordValidator: ValidatePassword,
    private val emailValidator: ValidateEmail,
    private val nameValidator: ValidateName,
    private val phoneNumberValidator: ValidatePhoneNumber,
    private val termsAndConditionsValidator: ValidateTermsAndConditions
) : BaseViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _registerEvent = Channel<RegisterEvent?>()
    val registerEvent = _registerEvent.receiveAsFlow()

    init {
        getInitialState()
    }

    private fun getInitialState() {
        _registerState.update { RegisterState() }
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Input.EnterUsername -> {
                _registerState.update {
                    it.copy(username = event.value)
                }
            }

            is RegisterEvent.Input.EnterFirstName -> {
                _registerState.update {
                    it.copy(firstName = event.value)
                }
            }

            is RegisterEvent.Input.EnterLastName -> {
                _registerState.update {
                    it.copy(lastName = event.value)
                }
            }

            is RegisterEvent.Input.EnterEmail -> {
                _registerState.update {
                    it.copy(email = event.value)
                }
            }

            is RegisterEvent.Input.EnterPhoneNumber -> {
                _registerState.update {
                    it.copy(phoneNumber = event.value)
                }
            }

            is RegisterEvent.Input.EnterPassword -> {
                _registerState.update {
                    it.copy(password = event.value)
                }
            }

            is RegisterEvent.Input.EnterConfirmPassword -> {
                _registerState.update {
                    it.copy(confirmPassword = event.value)
                }
            }

            is RegisterEvent.Input.AcceptTermsAndConditions -> _registerState.update {
                it.copy(
                    areTermsAndConditionsAccepted = event.isAccepted
                )
            }

            is RegisterEvent.Submit -> register()
            else -> {}
        }
    }

    fun register() {
        scope.launch {
            _registerState.update {
                it.copy(isLoading = true)
            }

            validateFields()

            if (_registerState.value.isInputValid) {
                try {
                    _registerState.value.run {
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
                    _registerEvent.send(RegisterEvent.Navigate.Login)
                } catch (e: Exception) {
                    _registerState.update {
                        it.copy(
                            error = e.message,
                            hasError = true,
                            isLoading = false
                        )
                    }
                    _registerEvent.send(RegisterEvent.Error(e.message ?: "An error occurred"))
                }
            }
        }
    }

    private fun validateFields() {
        val currentState = _registerState.value
        val usernameResponse = usernameValidator.validate(currentState.username)
        val emailResponse = emailValidator.validate(currentState.email)
        val firstNameResponse = nameValidator.validate(currentState.firstName)
        val lastNameResponse = nameValidator.validate(currentState.lastName)
        val phoneNumberResponse = phoneNumberValidator.validate(currentState.phoneNumber)
        val passwordResponse = passwordValidator.validate(currentState.password)
        val confirmPasswordResponse =
            passwordValidator.validate(currentState.password, currentState.confirmPassword)
        val termsAndConditionsResult =
            termsAndConditionsValidator.validate(currentState.areTermsAndConditionsAccepted)

        val hasError = listOf(
            usernameResponse,
            emailResponse,
            firstNameResponse,
            lastNameResponse,
            phoneNumberResponse,
            passwordResponse,
            confirmPasswordResponse
        ).any { !it.success }

        if (hasError) {
            _registerState.update {
                it.copy(
                    usernameErrorMessage = usernameResponse.error,
                    emailErrorMessage = emailResponse.error,
                    firstNameErrorMessage = firstNameResponse.error,
                    lastNameErrorMessage = lastNameResponse.error,
                    phoneNumberErrorMessage = phoneNumberResponse.error,
                    passwordErrorMessage = passwordResponse.error,
                    confirmPasswordErrorMessage = confirmPasswordResponse.error,
                    termsAndConditionsErrorMessage = termsAndConditionsResult.error,
                    isInputValid = false,
                    isLoading = false
                )
            }
            return
        }
    }
}