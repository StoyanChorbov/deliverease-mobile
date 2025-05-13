package xyz.deliverease.deliverease.delivery.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryRepository

class HomeViewModel(private val deliveryRepository: DeliveryRepository) : BaseViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState.asStateFlow()

    init {
        getDeliveries()
    }

    private fun getDeliveries() {
        scope.launch {
            _homeState.emit(HomeState(isLoading = true))

            try {
                val deliveries = deliveryRepository.getCurrentDeliveries()
                _homeState.emit(
                    HomeState(
                        toDeliver = deliveries.toDeliver,
                        toReceive = deliveries.toReceive
                    )
                )
            } catch (e: Exception) {
                _homeState.emit(HomeState(error = e.message))
            }
        }
    }
}

