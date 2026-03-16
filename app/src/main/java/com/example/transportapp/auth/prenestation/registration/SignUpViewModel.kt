package com.example.transportapp.auth.prenestation.registration

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.transportapp.core.ErrorMessages
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError.asStateFlow()

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()

    private val _isSignUpEnabled = MutableStateFlow(true)
    val isSignUpEnabled: StateFlow<Boolean> = _isSignUpEnabled.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _emailError.value = null
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = null
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordError.value = null
    }

    fun clearFields() {
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null
        _registrationState.value = RegistrationState.Idle
    }

    private fun checkAllFields(): Boolean {
        var isValid = true

        val currentEmail = _email.value
        val currentPassword = _password.value
        val currentConfirm = _confirmPassword.value

        if (currentEmail.isBlank()) {
            _emailError.value = ErrorMessages.REQUIRED_FIELD_ERROR
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            _emailError.value = ErrorMessages.EMAIL_FORMAT_ERROR
            isValid = false
        }

        if (currentPassword.isBlank()) {
            _passwordError.value = ErrorMessages.REQUIRED_FIELD_ERROR
            isValid = false
        } else if (currentPassword.length !in 8..20) {
            _passwordError.value = ErrorMessages.PASS_LENGTH_ERROR
            isValid = false
        } else if (!currentPassword.any { it.isUpperCase() }) {
            _passwordError.value = ErrorMessages.PASS_LETTERS_ERROR
            isValid = false
        } else if (!currentPassword.any { it.isDigit() }) {
            _passwordError.value = ErrorMessages.PASS_NUMBERS_ERROR
            isValid = false
        } else if (!currentPassword.any { !it.isLetterOrDigit() }) {
            _passwordError.value = ErrorMessages.SPECIAL_SYMBOLS
            isValid = false
        }

        if (currentConfirm.isBlank()) {
            _confirmPasswordError.value = ErrorMessages.REQUIRED_FIELD_ERROR
            isValid = false
        } else if (currentPassword != currentConfirm) {
            _confirmPasswordError.value = ErrorMessages.PASS_MATCH_ERROR
            isValid = false
        }

        return isValid
    }

    fun register() {
        if (!checkAllFields()) return

        _registrationState.value = RegistrationState.Loading
        _isSignUpEnabled.value = false

        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isSignUpEnabled.value = true
                if (task.isSuccessful) {
                    _registrationState.value = RegistrationState.Success
                } else {
                    _registrationState.value = RegistrationState.Error(
                        task.exception?.message ?: ErrorMessages.REGISTER_FAILED_ERROR
                    )
                }
            }
    }
}