package xyz.deliverease.deliverease.user.register

sealed class RegisterEvent {
    data object Idle : RegisterEvent()

    sealed class Input {
        data class EnterUsername(val value: String) : RegisterEvent()
        data class EnterFirstName(val value: String) : RegisterEvent()
        data class EnterLastName(val value: String) : RegisterEvent()
        data class EnterEmail(val value: String) : RegisterEvent()
        data class EnterPhoneNumber(val value: String) : RegisterEvent()
        data class EnterPassword(val value: String) : RegisterEvent()
        data class EnterConfirmPassword(val value: String) : RegisterEvent()
        data class AcceptTermsAndConditions(val isAccepted: Boolean) : RegisterEvent()
    }

    sealed class Navigate {
        data object Home : RegisterEvent()
        data object Login : RegisterEvent()
    }

    data object Submit : RegisterEvent()
    data class Error(val message: String) : RegisterEvent()
}