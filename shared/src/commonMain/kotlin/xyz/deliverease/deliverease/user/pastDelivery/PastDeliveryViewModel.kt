package xyz.deliverease.deliverease.user.pastDelivery

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryRepository

class PastDeliveryViewModel(private val deliveryRepository: DeliveryRepository) : BaseViewModel() {

    private val _pastDeliveryState = MutableStateFlow(PastDeliveryState())
    val pastDeliveryState = _pastDeliveryState.asStateFlow()

    init {
        getDeliveries()
    }

    private fun getDeliveries() {
        scope.launch {
            val deliveries = deliveryRepository.getPastDeliveries()
            _pastDeliveryState.update {
                it.copy(deliveries = deliveries)
            }
        }
    }
}

data class PastDeliveryState(
    val deliveries: List<UserDeliveryDTO> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)