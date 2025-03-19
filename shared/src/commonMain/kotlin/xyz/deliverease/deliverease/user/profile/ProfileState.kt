package xyz.deliverease.deliverease.user.profile

data class ProfileState(
    val profile: ProfileDTO = ProfileDTO(),
    val loading: Boolean = false,
    val error: String? = null
)