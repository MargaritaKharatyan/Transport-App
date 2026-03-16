package com.example.transportapp

import com.example.transportapp.auth.prenestation.forgotPass.ForgotPassViewModel
import com.example.transportapp.auth.prenestation.registration.SignUpViewModel
import com.example.transportapp.auth.prenestation.signIn.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainPresentationModule = module {
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { ForgotPassViewModel() }

}