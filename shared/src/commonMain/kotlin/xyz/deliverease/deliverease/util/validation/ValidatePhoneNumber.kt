package xyz.deliverease.deliverease.util.validation

class ValidatePhoneNumber {
    fun validate(phoneNumber: String): ValidationResult {
//        if (phoneNumber.isBlank()) {
//            return ValidationResult(
//                success = false,
//                error = "Phone number cannot be empty"
//            )
//        }
//
//        if (phoneNumber.length < 10) {
//            return ValidationResult(
//                success = false,
//                error = "Phone number must be at least 10 characters long"
//            )
//        }
//
//        if (phoneNumber.length > 15) {
//            return ValidationResult(
//                success = false,
//                error = "Phone number cannot be longer than 15 characters"
//            )
//        }
//
//        if (!phoneNumber.all { it.isDigit() || it == '-' }) {
//            return ValidationResult(
//                success = false,
//                error = "Phone number must contain only numbers and '-'"
//            )
//        }

        return ValidationResult(success = true)
    }
}