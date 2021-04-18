package com.dj.brownsmog.ui.local

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.data.model.SidoItem

@Composable
fun LocalDetailInfoScreen(
    sidoItem: SidoItem,
) {
    Log.e("check ", "check : " + sidoItem)
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end=8.dp)) {
        item {
            Text(text = sidoItem.stationName,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
        }
    }

}