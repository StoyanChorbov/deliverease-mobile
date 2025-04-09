package xyz.deliverease.deliverease.util.validation

class ValidateTermsAndConditions {
    fun validate(areAccepted: Boolean): ValidationResult =
        if (!areAccepted) {
            ValidationResult(
                success = false,
                error = "Please accept the terms and conditions"
            )
        }
        else ValidationResult(success = true)
}