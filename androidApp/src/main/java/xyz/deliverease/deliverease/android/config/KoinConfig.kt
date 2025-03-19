package xyz.deliverease.deliverease.android.config

import org.koin.dsl.module
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.user.login.LoginViewModel
import xyz.deliverease.deliverease.user.profile.ProfileViewModel
import xyz.deliverease.deliverease.user.register.RegisterViewModel

val androidAppModule = module {
    single { UserRepository() }
    single { RegisterViewModel(get()) }
    single { LoginViewModel(get()) }
    single { ProfileViewModel(get()) }
}