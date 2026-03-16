package com.example.transportapp.auth.prenestation.forgotPass

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPassViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _resetState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Idle)
    val resetState: StateFlow<ResetPasswordState> = _resetState.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _emailError.value = null
    }

    fun clearFields() {
        _email.value = ""
        _emailError.value = null
        _resetState.value = ResetPasswordState.Idle
    }

    private fun isEmailValid(): Boolean {
        if (_email.value.isBlank()) {
            _emailError.value = "This is a required field"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _emailError.value = "Check email format"
            return false
        }
        return true
    }

    fun resetPassword() {
        if (!isEmailValid()) return

        _resetState.value = ResetPasswordState.Loading
        _isButtonEnabled.value = false

        auth.fetchSignInMethodsForEmail(_email.value)
            .addOnCompleteListener { checkTask ->
                if (checkTask.isSuccessful) {
                    val signInMethods = checkTask.result?.signInMethods

                    if (signInMethods.isNullOrEmpty()) {
                        _resetState.value =
                            ResetPasswordState.Error("A user with this email wasn't found. Try another one.")
                        _isButtonEnabled.value = true
                    } else {
                        sendActualResetEmail()
                    }
                } else {
                    _resetState.value =
                        ResetPasswordState.Error("Check Error: ${checkTask.exception?.message}")
                    _isButtonEnabled.value = true
                }
            }
    }

    private fun sendActualResetEmail() {
        auth.sendPasswordResetEmail(_email.value)
            .addOnCompleteListener { task ->
                _isButtonEnabled.value = true
                if (task.isSuccessful) {
                    _resetState.value = ResetPasswordState.Success
                } else {
                    _resetState.value = ResetPasswordState.Error(
                        task.exception?.message ?: "Failed to send email."
                    )
                }
            }
    }
}