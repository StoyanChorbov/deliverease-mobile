package xyz.deliverease.deliverease.user.profile

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserDTO
import xyz.deliverease.deliverease.user.UserRepository

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
                // TODO: Change to actual user
                val profile = userRepository.login("pesho").toProfileDTO()

                _profileState.emit(ProfileState(profile = profile))
            } catch (e: Exception) {
                _profileState.emit(ProfileState(error = e.message))
            }
        }
    }

    private fun UserDTO.toProfileDTO() =
        ProfileDTO(username, firstName, lastName, email, phoneNumber)
}