package com.example.transportapp.core

object ErrorMessages {
    const val INVALID_DATA = "Invalid email or password."
    const val NETWORK_ERROR = "Network error. Please check your internet connection."
    const val PASSWORD_ERROR = "Password is required"
    const val EMPTY_EMAIL_ERROR = "Email cannot be empty"
    const val VALID_EMAIL_ERROR = "Enter a valid email (e.g., example@gmail.com)"
    const val REQUIRED_FIELD_ERROR = "This is a required field"
    const val PASS_MATCH_ERROR = "Passwords do not match"
    const val REGISTER_FAILED_ERROR = "Registration failed."
    const val EMAIL_FORMAT_ERROR = "Check email format"
    const val PASS_LENGTH_ERROR = "Password must be between 8 and 20 characters"
    const val PASS_LETTERS_ERROR = "Must contain at least one uppercase letter (A-Z)"
    const val PASS_NUMBERS_ERROR = "Must contain at least one number (0-9)"
    const val SPECIAL_SYMBOLS = "Must contain at least one special symbol"
    const val USER_FOUND_ERROR = "A user with this email wasn't found. Try another one."
    const val EMAIL_SENDING_ERROR = "Failed to send email."
    const val CHECK_ERROR = "Check Error: "
}