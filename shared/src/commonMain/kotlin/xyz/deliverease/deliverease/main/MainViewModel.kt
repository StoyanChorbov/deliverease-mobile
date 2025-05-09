package xyz.deliverease.deliverease.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.util.datastore.JwtTokenStorage

class MainViewModel(private val userRepository: UserRepository, private val jwtTokenStorage: JwtTokenStorage) : BaseViewModel() {

    private val _mainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> get() = _mainState.asStateFlow()

    init {
        checkLoggedIn()
        observeAuthToken()
    }

    private fun checkLoggedIn() {
        scope.launch {
            _mainState.emit(MainState(loading = true))

            try {
                userRepository.getProfile()
                _mainState.emit(MainState(loading = false, isLoggedIn = true))
            } catch (e: Exception) {
                _mainState.emit(MainState(error = e.message))
            }
        }
    }

    private fun observeAuthToken() {
        scope.launch {
            jwtTokenStorage.authTokenFlow.collect { token ->
                _mainState.update {
                    it.copy(
                        isLoggedIn = token != null,
                        loading = false
                    )
                }
            }
        }
    }
}