package xyz.deliverease.deliverease.delivery.find

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryRepository

class FindDeliveryViewModel(
    private val deliveryRepository: DeliveryRepository
) : BaseViewModel() {

    private val _findDeliveryState = MutableStateFlow(FindDeliveryState())
    val findDeliveryState = _findDeliveryState.asStateFlow()

    private val _findDeliveryEvent: Channel<FindDeliveryEvent> = Channel()
    val findDeliveryEvent = _findDeliveryEvent.receiveAsFlow()

    fun onEvent(event: FindDeliveryEvent) {
        when (event) {
            is FindDeliveryEvent.SetLocationQuery -> {
                val query = event.value
                _findDeliveryState.update {
                    it.copy(destinationQuery = query)
                }
                if (query.length > 2)
                    getLocationsFromQuery(query)
            }

            is FindDeliveryEvent.SetDestination -> {
                _findDeliveryState.update {
                    it.copy(destination = event.value)
                }
            }

            is FindDeliveryEvent.GetDeliveryOptions -> {
                _findDeliveryState.update {
                    it.copy(isLoading = true)
                }
                getDeliveryOptions(event.startingLocationLongitude, event.startingLocationLatitude)
            }

            else -> {}
        }
    }

    private fun getLocationsFromQuery(query: String) {
        scope.launch {
            val result = deliveryRepository.getLocationSuggestions(query)
            _findDeliveryState.update {
                it.copy(destinationSuggestions = result)
            }
        }
    }

    private fun getDeliveryOptions(
        startingLocationLongitude: Double,
        startingLocationLatitude: Double
    ) {
        scope.launch {
            val startingLocation = deliveryRepository.getLocationByCoordinates(startingLocationLongitude, startingLocationLatitude)
            val options =
                deliveryRepository.getDeliveryOptions(startingLocation.region, _findDeliveryState.value.destination.region)
            _findDeliveryState.update {
                it.copy(results = options, isLoading = false)
            }
        }
    }
}