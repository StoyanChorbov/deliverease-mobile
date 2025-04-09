package xyz.deliverease.deliverease.util.validation

class ValidatePassword {
    fun validate(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                success = false,
                error = "Password cannot be empty"
            )
        }

        if (password.length < 8) {
            return ValidationResult(
                success = false,
                error = "Password must be at least 8 characters long"
            )
        }

        val hasLettersAndNumbers = password.any { it.isLetter() } && password.any { it.isDigit() }
        if (!hasLettersAndNumbers) {
            return ValidationResult(
                success = false,
                error = "Password must contain both letters and numbers"
            )
        }

        return ValidationResult(success = true)
    }

    fun validate(password: String, confirmPassword: String): ValidationResult {
        val passwordValidation = validate(password)
        if (!passwordValidation.success) {
            return passwordValidation
        }

        if (password != confirmPassword) {
            return ValidationResult(
                success = false,
                error = "Passwords do not match"
            )
        }

        return ValidationResult(success = true)
    }
}