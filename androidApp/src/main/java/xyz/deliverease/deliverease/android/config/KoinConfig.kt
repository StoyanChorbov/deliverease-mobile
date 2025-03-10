package xyz.deliverease.deliverease.android.config

import org.koin.dsl.module
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.user.profile.ProfileViewModel

val androidAppModule = module {
    single { UserRepository() }
    single { ProfileViewModel(get()) }
}