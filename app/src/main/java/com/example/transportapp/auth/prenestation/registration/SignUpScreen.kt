package com.example.transportapp.auth.signIn.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transportapp.R
import com.example.transportapp.auth.prenestation.registration.RegistrationState
import com.example.transportapp.auth.prenestation.registration.SignUpViewModel
import com.example.transportapp.auth.prenestation.signIn.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.clearFields()
    }
    val context = LocalContext.current

    // States from ViewModel
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState() // Добавлено

    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()

    val isSignUpEnabled by viewModel.isSignUpEnabled.collectAsState()
    val registrationState by viewModel.registrationState.collectAsState()

    // Local UI States for password visibility
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(registrationState) {
        when (registrationState) {
            is RegistrationState.Success -> {
                Toast.makeText(context, "Account created successfully :)", Toast.LENGTH_SHORT).show()
                navigateToSignIn()
            }
            is RegistrationState.Error -> {
                Toast.makeText(context, (registrationState as RegistrationState.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.screen_background))
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight.W400,
                lineHeight = 36.sp,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 28.dp,
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.email),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    lineHeight = 18.sp,
                    letterSpacing = 0.15.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(3.dp))
                TextField(
                    value = email,
                    onValueChange = viewModel::onEmailChanged,
                    singleLine = true,
                    isError = emailError != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(30.dp),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.email_example),
                            fontWeight = FontWeight.W400,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.light_gray),
                        unfocusedContainerColor = colorResource(R.color.light_gray),
                        focusedPlaceholderColor = colorResource(R.color.text_white),
                        unfocusedPlaceholderColor = colorResource(R.color.text_white),
                        focusedTextColor = colorResource(R.color.text_white),
                        unfocusedTextColor = colorResource(R.color.text_white),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                if (emailError != null) {
                    Text(text = emailError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.password),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(3.dp))
                TextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChanged,
                    singleLine = true,
                    isError = passwordError != null,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) painterResource(R.drawable.ic_visibility) else painterResource(R.drawable.ic_visibility_off)
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = image,
                                contentDescription = description,
                                modifier = Modifier.size(18.dp),
                                tint = Color.White
                            )
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.enter_password),
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.light_gray),
                        unfocusedContainerColor = colorResource(R.color.light_gray),
                        focusedPlaceholderColor = colorResource(R.color.text_white),
                        unfocusedPlaceholderColor = colorResource(R.color.text_white),
                        focusedTextColor = colorResource(R.color.text_white),
                        unfocusedTextColor = colorResource(R.color.text_white),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                )
                if (passwordError != null) {
                    Text(text = passwordError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.repeat_pass),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(3.dp))
                TextField(
                    value = confirmPassword, // Привязано к confirmPassword!
                    onValueChange = viewModel::onConfirmPasswordChanged, // Вызов правильного метода!
                    singleLine = true,
                    isError = confirmPasswordError != null,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) painterResource(R.drawable.ic_visibility) else painterResource(R.drawable.ic_visibility_off)
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = image,
                                contentDescription = description,
                                modifier = Modifier.size(18.dp),
                                tint = Color.White
                            )
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.enter_password), // Или stringResource(R.string.repeat_pass)
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.light_gray),
                        unfocusedContainerColor = colorResource(R.color.light_gray),
                        focusedPlaceholderColor = colorResource(R.color.text_white),
                        unfocusedPlaceholderColor = colorResource(R.color.text_white),
                        focusedTextColor = colorResource(R.color.text_white),
                        unfocusedTextColor = colorResource(R.color.text_white),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                )
                if (confirmPasswordError != null) {
                    Text(text = confirmPasswordError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp))
                }
            }
        }

        Button(
            enabled = isSignUpEnabled,
            onClick = { viewModel.register() }, // Здесь вызываем register, а не navigateToSignIn
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.orange_btn),
                contentColor = colorResource(R.color.text_white),
                disabledContentColor = colorResource(R.color.text_white),
                disabledContainerColor = colorResource(R.color.orange_btn).copy(alpha = 0.5f), // Dim when loading
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(40.dp),
        ) {
            Text(
                text = if (registrationState is RegistrationState.Loading) "Signing up..." else stringResource(R.string.sign_up), // Изменил текст кнопки на Sign Up
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily(Font(R.font.roboto_semibold)),
                modifier = Modifier.fillMaxHeight().align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- ССЫЛКА НА ВХОД ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.sign_in),
                color = colorResource(R.color.orange_btn),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navigateToSignIn() }
            )
        }

    }
}

//
//@Composable
//fun SignUpScreen(
//    navigateToSignIn: () -> Unit,
//    viewModel: SignUpViewModel = koinViewModel()
//) {
//    val context = LocalContext.current
//
//    val email by viewModel.email.collectAsState()
//    val password by viewModel.password.collectAsState()
//    val confirmPassword by viewModel.confirmPassword.collectAsState()
//    val isEnabled by viewModel.isSignUpEnabled.collectAsState()
//    val registrationState by viewModel.registrationState.collectAsState()
//
//    LaunchedEffect(registrationState) {
//        when (registrationState) {
//            is RegistrationState.Success -> {
//                Toast.makeText(context, "Account created successfully :)", Toast.LENGTH_SHORT).show()
//                navigateToSignIn()
//            }
//            is RegistrationState.Error -> {
//                Toast.makeText(context, (registrationState as RegistrationState.Error).message, Toast.LENGTH_SHORT).show()
//            }
//            else -> {}
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(50.dp))
//        Text(text = "Sign Up", fontSize = 40.sp, fontWeight = FontWeight.Bold)
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Email
//        TextField(
//            value = email,
//            onValueChange = viewModel::onEmailChanged,
//            placeholder = { Text("Email") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//
//        // Password
//        TextField(
//            value = password,
//            onValueChange = viewModel::onPasswordChanged,
//            placeholder = { Text("Password") },
//            singleLine = true,
//            visualTransformation = PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//
//        // Confirm Password
//        TextField(
//            value = confirmPassword,
//            onValueChange = viewModel::onConfirmPasswordChanged,
//            placeholder = { Text("Confirm Password") },
//            singleLine = true,
//            visualTransformation = PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = { viewModel.register() },
//            enabled = isEnabled,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Sign Up")
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text(
//            text = "Already have an account? Sign In",
//            color = Color.Blue,
//            modifier = Modifier.clickable { navigateToSignIn() }
//        )
//    }
//}