package xyz.deliverease.deliverease.delivery.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryRepository

class HomeViewModel(private val deliveryRepository: DeliveryRepository) : BaseViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState.asStateFlow()

    init {
        getDeliveries()
    }

    private fun getDeliveries() {

    }
}

