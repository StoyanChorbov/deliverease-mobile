package xyz.deliverease.deliverease.android.config

import android.content.Context
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import xyz.deliverease.deliverease.delivery.DeliveryRepository
import xyz.deliverease.deliverease.delivery.NavigationService
import xyz.deliverease.deliverease.delivery.add.AddDeliveryViewModel
import xyz.deliverease.deliverease.delivery.details.DeliveryDetailsViewModel
import xyz.deliverease.deliverease.delivery.home.HomeViewModel
import xyz.deliverease.deliverease.user.UserRepository
import xyz.deliverease.deliverease.user.login.LoginViewModel
import xyz.deliverease.deliverease.user.profile.ProfileViewModel
import xyz.deliverease.deliverease.user.register.RegisterViewModel
import xyz.deliverease.deliverease.util.datastore.JwtTokenStorage
import xyz.deliverease.deliverease.util.validation.ValidateEmail
import xyz.deliverease.deliverease.util.validation.ValidateName
import xyz.deliverease.deliverease.util.validation.ValidatePassword
import xyz.deliverease.deliverease.util.validation.ValidatePhoneNumber
import xyz.deliverease.deliverease.util.validation.ValidateTermsAndConditions
import xyz.deliverease.deliverease.util.validation.ValidateUsername

val androidAppModule = module {

    // Utils - DataStore
    single { JwtTokenStorage(context = get()) }

    // Repositories/Services - Business Logic
    single { UserRepository(jwtTokenStorage = get()) }
    single { DeliveryRepository() }
    single { NavigationService() }

    // Validation
    single { ValidateUsername() }
    single { ValidatePassword() }
    single { ValidateEmail() }
    single { ValidateName() }
    single { ValidatePhoneNumber() }
    single { ValidateTermsAndConditions() }

    // ViewModels for screens
    viewModel {
        HomeViewModel(deliveryRepository = get())
    }
    viewModel {
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
    viewModel {
        LoginViewModel(
            userRepository = get(),
            usernameValidator = get(),
            passwordValidator = get()
        )
    }
    viewModel { ProfileViewModel(userRepository = get()) }
    viewModel { AddDeliveryViewModel(deliveryRepository = get()) }
    viewModel { (deliveryId: String) ->
        DeliveryDetailsViewModel(
            deliveryId = deliveryId,
            deliveryRepository = get()
        )
    }
}