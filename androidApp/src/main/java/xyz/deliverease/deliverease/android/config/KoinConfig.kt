package xyz.deliverease.deliverease.android.config

import org.koin.dsl.module
import xyz.deliverease.deliverease.delivery.DeliveryRepository
import xyz.deliverease.deliverease.delivery.NavigationService
import xyz.deliverease.deliverease.delivery.add.AddDeliveryViewModel
import xyz.deliverease.deliverease.delivery.home.HomeViewModel
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.user.login.LoginViewModel
import xyz.deliverease.deliverease.user.profile.ProfileViewModel
import xyz.deliverease.deliverease.user.register.RegisterViewModel
import xyz.deliverease.deliverease.util.validation.ValidateEmail
import xyz.deliverease.deliverease.util.validation.ValidateName
import xyz.deliverease.deliverease.util.validation.ValidatePassword
import xyz.deliverease.deliverease.util.validation.ValidatePhoneNumber
import xyz.deliverease.deliverease.util.validation.ValidateTermsAndConditions
import xyz.deliverease.deliverease.util.validation.ValidateUsername

val androidAppModule = module {
    // Repositories
    single { UserRepository() }
    single { DeliveryRepository() }

    // Validation
    single { ValidateUsername() }
    single { ValidatePassword() }
    single { ValidateEmail() }
    single { ValidateName() }
    single { ValidatePhoneNumber() }
    single { ValidateTermsAndConditions() }

    // ViewModels for screens
    single {
        HomeViewModel(deliveryRepository = get())
    }
    single {
        RegisterViewModel(
            userRepository = get(),
            usernameValidator = get(),
            passwordValidator = get(),
            emailValidator = get(),
            nameValidator = get(),
            phoneNumberValidator = get(),
            termsAndConditionsValidator = get()
        )
    }
    single {
        LoginViewModel(
            userRepository = get(),
            usernameValidator = get(),
            passwordValidator = get()
        )
    }
    single { ProfileViewModel(userRepository = get()) }
    single { AddDeliveryViewModel(deliveryRepository = get()) }
    single { NavigationService() }
}