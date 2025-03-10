package xyz.deliverease.deliverease

import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.deliverease.deliverease.delivery.DeliveryRepository

val appModule = module {
    single { DeliveryRepository() }
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}