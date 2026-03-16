package com.example.transportapp.main.routes.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transportapp.R
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun InteractiveTimer(
    intervalString: String,
    modifier: Modifier = Modifier
) {
    val intervalMinutes = remember(intervalString) {
        intervalString.toIntOrNull() ?: 10
    }

    var remainingSeconds by remember { mutableLongStateOf(0L) }

    LaunchedEffect(intervalMinutes) {
        val intervalInSeconds = intervalMinutes * 60

        while (true) {
            val calendar = Calendar.getInstance()
            val currentSecondsFromMidnight = (calendar.get(Calendar.HOUR_OF_DAY) * 3600) +
                    (calendar.get(Calendar.MINUTE) * 60) +
                    calendar.get(Calendar.SECOND)
            val secondsPassedInCycle = currentSecondsFromMidnight % intervalInSeconds
            val diff = intervalInSeconds - secondsPassedInCycle

            remainingSeconds = diff.toLong()

            kotlinx.coroutines.delay(1000L)
        }
    }

    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timeText = String.format("%02d:%02d", minutes, seconds)

    Text(
        modifier = modifier
            .background(colorResource(R.color.light_gray), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        text = timeText,
        fontSize = 16.sp,
        color = if (minutes < 1) Color.Red else colorResource(R.color.text),
        fontWeight = FontWeight.W500,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        lineHeight = 18.sp,
        letterSpacing = 0.15.sp,
    )
}