package xyz.deliverease.deliverease.delivery.home

import xyz.deliverease.deliverease.DeliveryListDTO

data class HomeState(
    val toDeliver: List<DeliveryListDTO> = emptyList(),
    val toReceive: List<DeliveryListDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)