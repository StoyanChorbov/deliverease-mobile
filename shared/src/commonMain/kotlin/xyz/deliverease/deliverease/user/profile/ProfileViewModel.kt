package xyz.deliverease.deliverease.user.profile

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val _profileState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> get() = _profileState.asStateFlow()

    private val _profileEvent: Channel<ProfileEvent> = Channel()
    val profileEvent = _profileEvent.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {
                getProfile()
            }
            is ProfileEvent.Logout -> {
                logout()
            }
            else -> {}
        }
    }

    init {
        getProfile()
    }

    private fun getProfile() {
        scope.launch {
            _profileState.emit(ProfileState(loading = true))

            try {
                val profile = userRepository.getProfile()

                if (profile == null) {
                    _profileState.emit(ProfileState(error = "Failed to load profile"))
                    return@launch
                }

                _profileState.emit(ProfileState(profile = profile))
            } catch (e: Exception) {
                _profileState.emit(ProfileState(error = e.message))
            }
        }
    }

    private fun logout() {
        scope.launch {
            _profileState.emit(ProfileState(loading = true))

            try {
                userRepository.logout()
                _profileState.update {
                    it.copy(profile = ProfileDTO(), loading = false)
                }
                _profileEvent.send(ProfileEvent.Redirect)
            } catch (e: Exception) {
                _profileState.emit(ProfileState(error = e.message))
            }
        }
    }
}