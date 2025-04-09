package xyz.deliverease.deliverease.util.validation

class ValidateName {
    fun validate(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                success = false,
                error = "Name cannot be empty"
            )
        }

        return ValidationResult(success = true)
    }
}