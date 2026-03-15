package com.example.transportapp.auth.prenestation.registration

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportapp.auth.domain.usecase.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    // Error states
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

        // Email validation
        if (_email.value.isBlank()) {
            _emailError.value = "This is a required field"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _emailError.value = "Check email format"
            isValid = false
        }

        // Password validation
        if (_password.value.isBlank()) {
            _passwordError.value = "This is a required field"
            isValid = false
        } else if (_password.value.length < 7) {
            _passwordError.value = "Password should be at least 7 characters long"
            isValid = false
        }

        // Confirm Password validation
        if (_confirmPassword.value.isBlank()) {
            _confirmPasswordError.value = "This is a required field"
            isValid = false
        } else if (_password.value != _confirmPassword.value) {
            _confirmPasswordError.value = "Passwords do not match"
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
                        task.exception?.message ?: "Registration failed."
                    )
                }
            }
    }
}