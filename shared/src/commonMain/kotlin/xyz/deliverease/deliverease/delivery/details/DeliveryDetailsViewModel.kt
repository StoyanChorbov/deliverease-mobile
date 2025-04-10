package xyz.deliverease.deliverease.delivery.details

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.deliverease.deliverease.BaseViewModel
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.DeliveryRepository
import xyz.deliverease.deliverease.delivery.Location

class DeliveryDetailsViewModel(
    private val deliveryId: String,
    private val deliveryRepository: DeliveryRepository
) : BaseViewModel() {
    private var _deliveryDetailsState = MutableStateFlow(DeliveryDetailsState())
    val deliveryDetailsState = _deliveryDetailsState.asStateFlow()

    init {
        loadDeliveryDetails()
    }

    private fun loadDeliveryDetails() {
        scope.launch {
            _deliveryDetailsState.update {
                it.copy(isLoading = true)
            }
            //TODO: Get data from backend
//            val deliveryDetails = deliveryRepository.getDeliveryDetails(deliveryId)
            val deliveryDetails = DeliveryDetailsDTO(
                id = deliveryId,
                name = "Test Delivery",
                description = "Some description this is",
                startingLocation = Location("Plovdiv", 0.0, 0.0),
                endingLocation = Location("Sofia", 1.0, 1.0),
                category = DeliveryCategory.Other,
                sender = "Pesho Maistora",
                recipients = setOf("Recipient 1", "Recipient 2"),
                isFragile = false
            )
            _deliveryDetailsState.update {
                it.copy(
                    name = deliveryDetails.name,
                    startLocation = deliveryDetails.startingLocation,
                    endLocation = deliveryDetails.endingLocation,
                    category = deliveryDetails.category,
                    recipients = deliveryDetails.recipients
                )
            }
        }
    }

}