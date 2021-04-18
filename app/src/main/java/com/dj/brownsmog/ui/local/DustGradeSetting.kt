package com.dj.brownsmog.ui.local

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DustGradeSetting(title: String, value: String?) {
    Row {
        value?.let {
            Text(text = "$title 등급: $it")
            Text(
                text = when (it.toInt()) {
                    1 -> "(좋음)"
                    2 -> "(보통)"
                    3 -> "(나쁨)"
                    4 -> "(매우 나쁨)"
                    else -> "(좋음)"
                },
                modifier = Modifier.padding(start = 8.dp),
                color = when (it.toInt()) {
                    1 -> Color.Blue
                    2 -> Color.Green
                    3 -> Color.Yellow
                    4 -> Color.Red
                    else -> Color.Blue
                },
                fontWeight = FontWeight.Bold)
        } ?: Text(text = "미세먼지 등급: 측정 불가")
    }
}