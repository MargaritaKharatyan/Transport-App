package com.example.transportapp.auth.prenestation.signIn

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.transportapp.core.ErrorMessages
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
            _emailError.value = ErrorMessages.EMPTY_EMAIL_ERROR
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            _emailError.value = ErrorMessages.VALID_EMAIL_ERROR
            isValid = false
        } else {
            _emailError.value = null
        }

        if (_password.value.isBlank()) {
            _passwordError.value = ErrorMessages.PASSWORD_ERROR
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
                        ErrorMessages.NETWORK_ERROR
                    } else {
                        ErrorMessages.INVALID_DATA
                    }
                    _loginState.value = LoginState.Error(errorMessage)
                }
            }
    }
}