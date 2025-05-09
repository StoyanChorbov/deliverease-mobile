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

    fun onEvent(event: AddDeliveryEvent) {
        when (event) {
            is AddDeliveryEvent.Input.SetName -> {
                _addDeliveryState.update {
                    it.copy(name = event.value)
                }
            }

            is AddDeliveryEvent.Input.SetDescription -> {
                _addDeliveryState.update {
                    it.copy(description = event.value)
                }
            }

            is AddDeliveryEvent.Input.SetDeliveryCategory -> {
                _addDeliveryState.update {
                    it.copy(category = event.value)
                }
            }

            is AddDeliveryEvent.Input.SetStartLocationQuery -> {
                val query = event.value
                _addDeliveryState.update {
                    it.copy(startLocationQuery = query)
                }
                if (query.length > 2)
                    getStartLocationSuggestions(query)
            }

            is AddDeliveryEvent.Input.SetStartLocation -> {
                _addDeliveryState.update {
                    it.copy(
                        startLocationQuery = event.place.formattedAddress(),
                        startLocationDto = event.place,
                        startLocationRegion = event.place.region
                    )
                }
            }

            is AddDeliveryEvent.Input.SetEndLocationQuery -> {
                val query = event.value
                _addDeliveryState.update {
                    it.copy(endLocationQuery = query)
                }
                if (query.length > 2)
                    getEndLocationSuggestions(query)
            }

            is AddDeliveryEvent.Input.SetEndLocation -> {
                _addDeliveryState.update {
                    it.copy(
                        endLocationQuery = event.place.formattedAddress(),
                        endLocationDto = event.place,
                        endLocationRegion = event.place.region
                    )
                }
            }

            is AddDeliveryEvent.Input.SetIsFragile -> {
                _addDeliveryState.update {
                    it.copy(isFragile = event.value)
                }
            }

            is AddDeliveryEvent.Input.ChangeCurrentRecipient -> {
                _addDeliveryState.update {
                    it.copy(currentRecipient = event.value)
                }
            }

            is AddDeliveryEvent.Input.AddRecipient -> {
                _addDeliveryState.update {
                    it.copy(
                        currentRecipient = "",
                        recipients = it.recipients + it.currentRecipient
                    )
                }
            }

            is AddDeliveryEvent.Input.RemoveRecipient -> {
                _addDeliveryState.update {
                    it.copy(recipients = it.recipients - event.value)
                }
            }

            is AddDeliveryEvent.Submit -> {
                addDelivery()
            }

            is AddDeliveryEvent.Error -> {
                _addDeliveryState.update {
                    it.copy(error = event.message, hasError = true)
                }
            }

            else -> {}
        }
    }

    private fun addDelivery() {
        scope.launch {
            _addDeliveryState.update {
                it.copy(isLoading = true)
            }

            validateFields()

            val currentState = _addDeliveryState.value

            if (currentState.isInputValid) {
                try {
                    val deliveryId = deliveryRepository.addDelivery(currentState.toDTO())
                    _addDeliveryState.update {
                        it.copy(isLoading = false)
                    }
                    _addDeliveryEvent.send(AddDeliveryEvent.Navigate(deliveryId))
                } catch (e: Exception) {
                    _addDeliveryState.update {
                        it.copy(error = e.message, hasError = true, isLoading = false)
                    }
                }
            }
        }
    }

    private fun getStartLocationSuggestions(query: String) {
        scope.launch {
            val suggestions = deliveryRepository.getLocationSuggestions(query)
            _addDeliveryState.update {
                it.copy(startLocationSuggestions = suggestions)
            }
        }
    }

    private fun getEndLocationSuggestions(query: String) {
        scope.launch {
            val suggestions = deliveryRepository.getLocationSuggestions(query)
            _addDeliveryState.update {
                it.copy(endLocationSuggestions = suggestions)
            }
        }
    }

    private fun validateFields() {
    }

    private fun AddDeliveryState.toDTO(): AddDeliveryDTO {
        return AddDeliveryDTO(
            name = name,
            startLocation = startLocationDto,
            startLocationRegion = startLocationRegion,
            endLocation = endLocationDto,
            endLocationRegion = endLocationRegion,
            description = description,
            category = category,
            recipients = recipients,
            isFragile = isFragile
        )
    }
}