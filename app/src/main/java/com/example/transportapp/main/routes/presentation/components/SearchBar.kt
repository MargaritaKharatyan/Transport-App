package com.example.transportapp.main.routes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transportapp.R

@Composable
fun SearchBar(
    text: String,
    onClearClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(41.dp))
            .background(colorResource(R.color.light_gray))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (text.isEmpty()) {
            Text(
                text = stringResource(R.string.search_bar_hint_text),
                color = colorResource(R.color.text_white),
                style = TextStyle(fontSize = 14.sp)
            )
        }
        BasicTextField(
            value = text,
            onValueChange = onSearchClick,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearchClick(text)
                    keyboardController?.hide()
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    if (text.isNotEmpty()) {
                        IconButton(onClick = onClearClick) {
                            Icon(
                                painter = painterResource(R.drawable.ic_clear),
                                contentDescription = "Clear text",
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search text",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onSearchClick(text)
                                keyboardController?.hide()
                            },
                        tint = Color.White
                    )
                }
            }
        )
    }
}