package xyz.deliverease.deliverease.util.validation

data class ValidationResult(
    val success: Boolean,
    val error: String? = null
)
