package xyz.deliverease.deliverease.delivery.details

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryRepository
import xyz.deliverease.deliverease.delivery.LocationDto
import xyz.deliverease.deliverease.user.UserRepository

class DeliveryDetailsViewModel(
    private val deliveryId: String,
    private val deliveryRepository: DeliveryRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _deliveryDetailsState = MutableStateFlow(DeliveryDetailsState())
    val deliveryDetailsState = _deliveryDetailsState.asStateFlow()

    private val _deliveryDetailsEvent: Channel<DeliveryDetailsEvent> = Channel()
    val deliveryDetailsEvent = _deliveryDetailsEvent.receiveAsFlow()

    init {
        loadDeliveryDetails()
    }

    fun onEvent(event: DeliveryDetailsEvent) {
        when (event) {
            is DeliveryDetailsEvent.DeliverPackage -> {
                scope.launch {
                    try {
                        deliveryRepository.deliverPackage(DeliveryRequestDto(deliveryId))
                    } catch (e: Exception) {
                        _deliveryDetailsState.update {
                            it.copy(error = e.message)
                        }
                    }
                }
            }
            is DeliveryDetailsEvent.MarkAsDelivered -> {
                scope.launch {
                    try {
                        deliveryRepository.markAsDelivered(DeliveryRequestDto(deliveryId))
                    } catch (e: Exception) {
                        _deliveryDetailsState.update {
                            it.copy(error = e.message)
                        }
                    }
                }
            }

            is DeliveryDetailsEvent.ConnectToLocationServer -> {
                connectToLocationServer()
            }

            is DeliveryDetailsEvent.DisconnectFromLocationServer -> {
                disconnectFromLocationServer()
            }

            is DeliveryDetailsEvent.SendLocationUpdate -> {
                sendLocationUpdate(event.recipientUsername, event.latitude, event.longitude)
            }

            is DeliveryDetailsEvent.RequestLocationUpdate -> {
                requestLocationUpdate()
            }

            else -> {}
        }
    }

    private fun loadDeliveryDetails() {
        scope.launch {
            _deliveryDetailsState.update {
                it.copy(isLoading = true)
            }
            val deliveryDetails = deliveryRepository.getDeliveryDetails(deliveryId)
            val username: String = userRepository.getProfile()?.username ?: ""
            _deliveryDetailsState.update {
                it.copy(
                    name = deliveryDetails.name,
                    startLocationDto = deliveryDetails.startingLocation,
                    endLocationDto = deliveryDetails.endingLocation,
                    category = deliveryDetails.category,
                    sender = deliveryDetails.sender,
                    recipients = deliveryDetails.recipients,
                    deliverer = deliveryDetails.deliverer,
                    user = username
                )
            }
        }
    }

    private fun connectToLocationServer() {
        scope.launch {
            try {
                _deliveryDetailsState.value.let { state ->
                    if (state.deliverer == state.user) {
                        deliveryRepository.connectAsDeliverer { username ->
                            _deliveryDetailsEvent.send(DeliveryDetailsEvent.TriggerLocationUpdate(username))
                        }
                    } else {
                        deliveryRepository.connectAsRecipient { latitude, longitude ->
                            _deliveryDetailsState.update {
                                it.copy(delivererLocation = LocationDto(latitude = latitude, longitude = longitude))
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _deliveryDetailsState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }

    private fun requestLocationUpdate() {
        scope.launch {
            try {
                deliveryRepository.requestLocationUpdate(_deliveryDetailsState.value.deliverer ?: "")
            } catch (e: Exception) {
                _deliveryDetailsState.update {
                    it.copy(error = e.message)
                }
            } catch (e: Exception) {
                _deliveryDetailsState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }

    private fun sendLocationUpdate(recipientUsername: String, latitude: Double, longitude: Double) {
        scope.launch {
            try {
                deliveryRepository.sendLocationUpdate(
                    recipientUsername,
                    latitude,
                    longitude
                )
            } catch (e: Exception) {
                _deliveryDetailsState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }

    private fun disconnectFromLocationServer() {
        scope.launch {
            try {
                deliveryRepository.stopSharingLocation()
            } catch (e: Exception) {
                _deliveryDetailsState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }
}