package com.example.transportapp.auth.prenestation.signIn

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
import androidx.compose.material3.HorizontalDivider
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
import org.koin.androidx.compose.koinViewModel

//@Composable
//fun SignInScreen(
//    navigateToMain: () -> Unit,
//    navigateToSignUp: () -> Unit,
//    navigateToForgotPass: () -> Unit,
//    viewModel: SignInViewModel = koinViewModel(),
//) {
//    LaunchedEffect(Unit) {
//        viewModel.clearFields()
//    }
//    val context = LocalContext.current
//    val email by viewModel.email.collectAsState()
//    val password by viewModel.password.collectAsState()
//    val isLoginEnabled: Any by viewModel.isLoginEnabled.collectAsState()
//    val loginState by viewModel.loginState.collectAsState()
//
//    LaunchedEffect(loginState) {
//        when (loginState) {
//            is LoginState.Success -> navigateToMain()
//            is LoginState.Error -> {
//                Toast.makeText(
//                    context,
//                    (loginState as LoginState.Error).message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            else -> {}
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(R.color.screen_background))
//            .padding(WindowInsets.systemBars.asPaddingValues())
//            .padding(horizontal = 16.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 100.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = stringResource(R.string.sign_in),
//                fontSize = 28.sp,
//                fontFamily = FontFamily(Font(R.font.roboto_regular)),
//                fontWeight = FontWeight.W400,
//                lineHeight = 36.sp,
//                color = Color.White
//            )
//        }
//
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    top = 28.dp,
//                )
//        ) {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    modifier = Modifier,
//                    text = stringResource(R.string.email),
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.W500,
//                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
//                    lineHeight = 18.sp,
//                    letterSpacing = 0.15.sp,
//                    color = Color.White
//                )
//                Spacer(modifier = Modifier.height(3.dp))
//                TextField(
//                    value = email,
//                    onValueChange = viewModel::onEmailChanged,
//                    singleLine = true,
//                    shape = RoundedCornerShape(30.dp),
//                    placeholder = {
//                        Text(
//                            text = stringResource(R.string.email_example),
//                            fontWeight = FontWeight.W400,
//                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
//                        )
//                    },
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = colorResource(R.color.light_gray),
//                        unfocusedContainerColor = colorResource(R.color.light_gray),
//                        focusedPlaceholderColor = colorResource(R.color.text_white),
//                        unfocusedPlaceholderColor = colorResource(R.color.text_white),
//                        focusedTextColor = colorResource(R.color.text_white),
//                        unfocusedTextColor = colorResource(R.color.text_white),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 5.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    modifier = Modifier,
//                    text = stringResource(R.string.password),
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.W500,
//                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
//                    lineHeight = 18.sp,
//                    letterSpacing = 0.15.sp,
//                    color = Color.White
//                )
//                Spacer(modifier = Modifier.height(3.dp))
//                TextField(
//                    value = password,
//                    onValueChange = viewModel::onPasswordChanged,
//                    singleLine = true,
//                    shape = RoundedCornerShape(30.dp),
//                    placeholder = {
//                        Text(
//                            text = stringResource(R.string.enter_password),
//                            fontWeight = FontWeight.W400,
//                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
//                        )
//                    },
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = colorResource(R.color.light_gray),
//                        unfocusedContainerColor = colorResource(R.color.light_gray),
//                        focusedPlaceholderColor = colorResource(R.color.text_white),
//                        unfocusedPlaceholderColor = colorResource(R.color.text_white),
//                        focusedTextColor = colorResource(R.color.text_white),
//                        unfocusedTextColor = colorResource(R.color.text_white),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 5.dp)
//                )
//            }
//        }
//
//        Button(
//            enabled = isLoginEnabled as Boolean,
//            onClick = {
//                viewModel.login()
////                navigateToMain()
//            },
//            shape = RoundedCornerShape(30.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(R.color.green_btn),
//                contentColor = colorResource(R.color.text_white),
//                disabledContentColor = colorResource(R.color.text_white),
//                disabledContainerColor = colorResource(R.color.green_btn),
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 24.dp)
//                .height(40.dp),
//        ) {
//            Text(
//                text = stringResource(R.string.sign_in),
//                fontWeight = FontWeight.W600,
//                fontFamily = FontFamily(Font(R.font.roboto_semibold)),
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .align(Alignment.CenterVertically)
//                    .height(20.dp),
//                textAlign = TextAlign.Center,
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        HorizontalDivider(
//            thickness = 1.dp,
//            color = colorResource(R.color.divider)
//        )
//
//        Column(
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(top = 16.dp),
//        ) {
//            Row {
//                Text(
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = FontFamily(Font(R.font.roboto_semibold)),
//                    text = stringResource(R.string.no_account),
//                    color = colorResource(R.color.text_white),
//                )
//                Text(
//                    modifier = Modifier.clickable { navigateToSignUp() },
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = FontFamily(Font(R.font.roboto_semibold)),
//                    text = stringResource(R.string.registration),
//                    color = colorResource(R.color.green_btn),
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//            ) {
//                Text(
//                    modifier = Modifier.clickable { navigateToForgotPass() },
//                    fontSize = 12.sp,
//                    text = stringResource(R.string.forgot_pass),
//                    color = colorResource(R.color.green_btn),
//                )
//            }
//        }
//
//    }
//}
//
//
//package com.example.transportapp.auth.prenestation.signIn
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.transportapp.R
//import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgotPass: () -> Unit,
    viewModel: SignInViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.clearFields()
    }
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    // New error states from ViewModel
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> navigateToMain()
            is LoginState.Error -> {
                Toast.makeText(
                    context,
                    (loginState as LoginState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
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
                text = stringResource(R.string.sign_in),
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
                .padding(top = 28.dp)
        ) {
            // --- EMAIL FIELD ---
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
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
                    /* Uncomment and replace with your actual drawable if needed
                    leadingIcon = {
                        Icon(painterResource(id = R.drawable.ic_email), contentDescription = null)
                    }, */
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
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- PASSWORD FIELD ---
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.password),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    lineHeight = 18.sp,
                    letterSpacing = 0.15.sp,
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
                        val image = if (passwordVisible)
                            painterResource(id = R.drawable.ic_visibility_off)
                        else
                            painterResource(id = R.drawable.ic_visibility)

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
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Button(
            enabled = isLoginEnabled,
            onClick = { viewModel.login() },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.green_btn),
                contentColor = colorResource(R.color.text_white),
                disabledContentColor = colorResource(R.color.text_white),
                disabledContainerColor = colorResource(R.color.green_btn).copy(alpha = 0.5f), // Dim when loading
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(40.dp),
        ) {
            Text(
                text = if (loginState is LoginState.Loading) "Signing in..." else stringResource(R.string.sign_in),
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily(Font(R.font.roboto_semibold)),
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(R.color.divider)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
        ) {
            Row {
                Text(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily(Font(R.font.roboto_semibold)),
                    text = stringResource(R.string.no_account) + " ",
                    color = colorResource(R.color.text_white),
                )
                Text(
                    modifier = Modifier.clickable { navigateToSignUp() },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily(Font(R.font.roboto_semibold)),
                    text = stringResource(R.string.registration),
                    color = colorResource(R.color.green_btn),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = Modifier.clickable { navigateToForgotPass() },
                    fontSize = 12.sp,
                    text = stringResource(R.string.forgot_pass),
                    color = colorResource(R.color.green_btn),
                )
            }
        }
    }
}