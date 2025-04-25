package xyz.deliverease.deliverease.delivery.add

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.DeliveryRepository
import xyz.deliverease.deliverease.delivery.LocationDto

class AddDeliveryViewModel(private val deliveryRepository: DeliveryRepository) : BaseViewModel() {

    private val _addDeliveryState = MutableStateFlow(AddDeliveryState())
    val addDeliveryState = _addDeliveryState.asStateFlow()

    private val _addDeliveryEvent: Channel<AddDeliveryEvent> = Channel()
    val addDeliveryEvent = _addDeliveryEvent.receiveAsFlow()

    init {
        getInitialState()
    }

    private fun getInitialState() {
        _addDeliveryState.update { AddDeliveryState() }
    }

    fun addDelivery() {
        scope.launch {
            _addDeliveryState.update {
                it.copy(isLoading = true)
            }

            validateFields()

            val currentState = _addDeliveryState.value

            if (currentState.isInputValid) {
                try {
                    deliveryRepository.addDelivery(currentState.toDTO())
                    _addDeliveryState.update {
                        it.copy(isLoading = false)
                    }
                } catch (e: Exception) {
                    _addDeliveryState.update {
                        it.copy(error = e.message, hasError = true, isLoading = false)
                    }
                }
            }
        }
    }

    private fun validateFields() {
        //TODO("Not yet implemented")
    }

    fun setName(name: String) {
        _addDeliveryState.update {
            it.copy(name = name)
        }
    }

    fun setStartLocation(startLocationDto: LocationDto) {
        _addDeliveryState.update {
            it.copy(startLocationDto = startLocationDto)
        }
    }

    fun setEndLocation(endLocationDto: LocationDto) {
        _addDeliveryState.update {
            it.copy(endLocationDto = endLocationDto)
        }
    }

    fun setDescription(description: String) {
        _addDeliveryState.update {
            it.copy(description = description)
        }
    }

    fun setDeliveryCategory(deliveryCategory: DeliveryCategory) {
        _addDeliveryState.update {
            it.copy(category = deliveryCategory)
        }
    }

    fun setIsFragile(isFragile: Boolean) {
        _addDeliveryState.update {
            it.copy(isFragile = isFragile)
        }
    }

    fun setCurrentRecipient(currentRecipient: String) {
        _addDeliveryState.update {
            it.copy(currentRecipient = currentRecipient)
        }
    }

    fun addRecipient() {
        _addDeliveryState.update {
            it.copy(
                currentRecipient = "",
                recipients = it.recipients + it.currentRecipient
            )
        }
    }

    fun removeRecipient(recipient: String) {
        _addDeliveryState.update {
            it.copy(recipients = it.recipients - recipient)
        }
    }

    private fun AddDeliveryState.toDTO(): AddDeliveryDTO {
        return AddDeliveryDTO(
            name = name,
            startLocationDto = startLocationDto,
            endLocationDto = endLocationDto,
            description = description,
            category = category,
            recipients = recipients,
            isFragile = isFragile
        )
    }
}