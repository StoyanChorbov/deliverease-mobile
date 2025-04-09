package xyz.deliverease.deliverease.util.validation

class ValidateUsername {
    fun validate (username: String): ValidationResult {
        if (username.isBlank()) {
            return ValidationResult(
                success = false,
                error = "Username cannot be empty"
            )
        }

        if (username.contains("@")) {
            return ValidationResult(
                success = false,
                error = "Username cannot contain '@'"
            )
        }

        if (username.length < 3) {
            return ValidationResult(
                success = false,
                error = "Username must be at least 3 characters long"
            )
        }

        if (username.length > 52) {
            return ValidationResult(
                success = false,
                error = "Username cannot be longer than 52 characters"
            )
        }

        if (!username.all { it.isLetterOrDigit() }) {
            return ValidationResult(
                success = false,
                error = "Username must contain only letters and numbers"
            )
        }

        return ValidationResult(success = true)
    }
}