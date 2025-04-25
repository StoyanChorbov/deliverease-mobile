package xyz.deliverease.deliverease.main

data class MainState(
    val isLoggedIn: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
