package xyz.deliverease.deliverease.user.login

sealed class LoginEvent {
    data object Idle : LoginEvent()

    sealed class Input {
        data class EnterUsername(val value: String) : LoginEvent()
        data class EnterPassword(val value: String) : LoginEvent()
    }

    sealed class Navigate {
        data object Home : LoginEvent()
        data object Register : LoginEvent()
    }

    data object Submit : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}