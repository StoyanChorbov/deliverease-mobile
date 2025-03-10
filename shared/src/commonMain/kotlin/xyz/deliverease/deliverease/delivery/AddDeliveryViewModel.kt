package xyz.deliverease.deliverease.delivery

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel

class AddDeliveryViewModel(private val deliveryRepository: DeliveryRepository) : BaseViewModel() {
    private val _delivery = MutableStateFlow(DeliveryDTO())
    val delivery: StateFlow<DeliveryDTO> get() = _delivery.asStateFlow()

    fun addDelivery(deliveryDTO: DeliveryDTO) {
        scope.launch {
            try {
                val responseCode = deliveryRepository.addDelivery(deliveryDTO)

                if (responseCode.value == 201) {
                    TODO("Handle success")
                } else {
                    TODO("Handle failure")
                }
            } catch (e: Exception) {
                TODO("Handle exception")
            }
        }
    }
}