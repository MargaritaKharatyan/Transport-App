package com.example.transportapp.auth.prenestation.signIn

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transportapp.auth.domain.usecase.SignInUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//class SignInViewModel(
//    private val signInUseCase: SignInUseCase
//) : ViewModel() {
//
//    private val _email = MutableStateFlow("")
//    val email: StateFlow<String> = _email
//
//    private val _password = MutableStateFlow("")
//    val password: StateFlow<String> = _password
//
//    private val _isLoginEnabled = MutableStateFlow(false)
//    val isLoginEnabled: StateFlow<Boolean> = _isLoginEnabled
//
//    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
//    val loginState: StateFlow<LoginState> = _loginState
//
//    fun onEmailChanged(value: String) {
//        _email.value = value
//        validate()
//    }
//
//    fun onPasswordChanged(value: String) {
//        _password.value = value
//        validate()
//    }
//
//    private fun validate() {
//        _isLoginEnabled.value =
//            Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
//                    && _password.value.length >= 8
//    }
//
//    fun login() {
//        viewModelScope.launch {
//            _loginState.value = LoginState.Loading
//            try {
//                signInUseCase(email.value, password.value)
//                _loginState.value = LoginState.Success
//            } catch (e: Exception) {
//                _loginState.value = LoginState.Error(e.message ?: "Login failed")
//            }
//        }
//    }
//
//    fun clearFields() {
//        _email.value = ""
//        _password.value = ""
//        _loginState.value = LoginState.Idle
//    }
//}


//package com.example.transportapp.auth.prenestation.signIn
//
//import android.util.Patterns
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow

//sealed class LoginState {
//    object Idle : LoginState()
//    object Loading : LoginState()
//    object Success : LoginState()
//    data class Error(val message: String) : LoginState()
//}

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
        _emailError.value = null // Clear error when user types
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = null // Clear error when user types
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

        if (_email.value.isBlank()) {
            _emailError.value = "This is a required field"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _emailError.value = "Check email format"
            isValid = false
        } else {
            _emailError.value = null
        }

        if (_password.value.isBlank()) {
            _passwordError.value = "This is a required field"
            isValid = false
        } else if (_password.value.length < 7) {
            _passwordError.value = "Password should be at least 7 characters long"
            isValid = false
        } else {
            _passwordError.value = null
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
                    _loginState.value = LoginState.Error("Error!\nEntered data is incorrect.")
                }
            }
    }
}