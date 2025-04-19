package xyz.deliverease.deliverease.user.profile

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserDTO
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.user.login.UserLoginDTO

class ProfileViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val _profileState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> get() = _profileState.asStateFlow()

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
}