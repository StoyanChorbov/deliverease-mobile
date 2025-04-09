package xyz.deliverease.deliverease.util.validation

class ValidateEmail {
    private val emailPattern = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                ")+"
    )

    fun validate(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                success = false,
                error = "Email cannot be empty"
            )
        }

        if (!emailPattern.matches(email)) {
            return ValidationResult(
                success = false,
                error = "Invalid email format"
            )
        }

        return ValidationResult(success = true)
    }
}