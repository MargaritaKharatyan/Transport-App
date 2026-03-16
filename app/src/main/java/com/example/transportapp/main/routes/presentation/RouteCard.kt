package com.example.transportapp.main.routes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transportapp.R
import com.example.transportapp.common.domain.TransportRoute

@Composable
fun RouteCard(
    route: TransportRoute,
    modifier: Modifier = Modifier,
    navigateToMap: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_milk))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .padding(start = 16.dp, end = 12.dp),
        ) {

            Text(
                text = route.firstStop + " -> " + route.lastStop,
                fontSize = 16.sp,
                color = colorResource(R.color.text_white),
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                lineHeight = 18.sp,
                letterSpacing = 0.15.sp,
            )


            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = route.type,
                    color = colorResource(R.color.text_white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    lineHeight = 18.sp,
                    letterSpacing = 0.15.sp,
                )
                Row(
                    modifier = Modifier.clickable{navigateToMap(route.id)}
                ) {
                    Text(
                        text = stringResource(R.string.show_route),
                        color = colorResource(R.color.green_btn),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = FontFamily(Font(R.font.roboto_semibold)),
                        lineHeight = 15.sp,
                        letterSpacing = 0.4.sp,
                    )
                    Box(
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_right_short_fill),
                            contentDescription = "",
                            tint = colorResource(R.color.green_btn)
                        )
                    }
                }
            }
        }

    }
}