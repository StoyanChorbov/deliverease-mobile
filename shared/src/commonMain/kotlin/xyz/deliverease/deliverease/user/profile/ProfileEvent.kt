package xyz.deliverease.deliverease.user.profile

sealed class ProfileEvent {
    data object GetProfile : ProfileEvent()
    data object Logout : ProfileEvent()
    data object Redirect : ProfileEvent()
    data class Loading(val loading: Boolean) : ProfileEvent()
    data class Error(val message: String) : ProfileEvent()
    data object Idle : ProfileEvent()
}