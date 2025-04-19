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

        val hasUppercaseAndLowercase = password.any { it.isUpperCase() } && password.any { it.isLowerCase() }
        val hasNumbers = password.any { it.isDigit() }
        val hasSpecialCharacters = password.any { !it.isLetterOrDigit() }

        if (!hasUppercaseAndLowercase || !hasNumbers || !hasSpecialCharacters) {
            return ValidationResult(
                success = false,
                error = "Password must contain uppercase and lowercase letters, numbers, and special characters"
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