package com.example.transportapp.auth.prenestation.signIn

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // States for field validation errors
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _isLoginEnabled = MutableStateFlow(true)
    val isLoginEnabled: StateFlow<Boolean> = _isLoginEnabled.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _emailError.value = null
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = null
    }

    fun clearFields() {
        _email.value = ""
        _password.value = ""
        _emailError.value = null
        _passwordError.value = null
        _loginState.value = LoginState.Idle
    }

    private fun checkAllFields(): Boolean {
        var isValid = true
        val emailValue = _email.value.trim()

        if (emailValue.isBlank()) {
            _emailError.value = "Email cannot be empty"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            _emailError.value = "Enter a valid email (e.g., example@gmail.com)"
            isValid = false
        } else {
            _emailError.value = null
        }

        if (_password.value.isBlank()) {
            _passwordError.value = "Password is required"
            isValid = false
        }

        return isValid
    }

    fun login() {
        if (!checkAllFields()) return

        _loginState.value = LoginState.Loading
        _isLoginEnabled.value = false

        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isLoginEnabled.value = true
                if (task.isSuccessful) {
                    _loginState.value = LoginState.Success
                } else {
                    val exception = task.exception
                    val errorMessage = if (exception is FirebaseNetworkException) {
                        "Network error. Please check your internet connection."
                    } else {
                        "Invalid email or password."
                    }
                    _loginState.value = LoginState.Error(errorMessage)
                }
            }
    }
}