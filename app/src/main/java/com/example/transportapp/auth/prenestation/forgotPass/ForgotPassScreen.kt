package com.example.transportapp.auth.prenestation.forgotPass

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transportapp.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPassScreen(
    navigateToSignIn: () -> Unit,
    viewModel: ForgotPassViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()
    val resetState by viewModel.resetState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearFields()
    }

    LaunchedEffect(resetState) {
        when (resetState) {
            is ResetPasswordState.Success -> {
                Toast.makeText(context, R.string.mail_sent, Toast.LENGTH_LONG)
                    .show()
                navigateToSignIn()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.screen_background))
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            val image = painterResource(R.drawable.ic_back)
            IconButton(
                onClick = { navigateToSignIn() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-12).dp)
            ) {
                Icon(
                    painter = image,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.forgot_pass),
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight.W400,
                lineHeight = 36.sp,
                color = Color.White
            )
        }

        Text(
            text = stringResource(R.string.sending_mail),
            color = Color.LightGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.email),
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
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
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(R.color.light_gray),
                    unfocusedContainerColor = colorResource(R.color.light_gray),
                    focusedPlaceholderColor = colorResource(R.color.text_white),
                    unfocusedPlaceholderColor = colorResource(R.color.text),
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
            if (resetState is ResetPasswordState.Error) {
                Text(
                    text = (resetState as ResetPasswordState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Start
                )
            }
        }

        Button(
            enabled = isButtonEnabled,
            onClick = { viewModel.resetPassword() },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.reset_pass_btn),
                contentColor = colorResource(R.color.text_white),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(40.dp),
        ) {
            Text(
                text = if (resetState is ResetPasswordState.Loading) stringResource(R.string.send) else stringResource(
                    R.string.reset_pass
                ),
                fontWeight = FontWeight.W600,
                fontFamily = FontFamily(Font(R.font.roboto_semibold)),
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
        }
    }
}