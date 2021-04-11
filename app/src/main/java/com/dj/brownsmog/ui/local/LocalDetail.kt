package com.dj.brownsmog.ui.local

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dj.brownsmog.R
import com.dj.brownsmog.data.model.SidoItem

@Composable
fun LocalDetailScreen(
    navController: NavHostController,
    viewModel: LocalDetailViewModel,
    sidoName: String,
) {
    val onLoad = viewModel.onLoad.collectAsState()
    if (!onLoad.value) {
        viewModel.setLoaded()
        viewModel.getSidoByulItems(sidoName = sidoName)
    }
    val sidoItems by viewModel.sidoByulItems.collectAsState()
    LocalDetailList(sidoItemList = sidoItems)
}

@Composable
fun LocalDetailList(sidoItemList: List<SidoItem>?) {
    sidoItemList?.let { list ->
        LazyColumn {
            items(items = list) { sidoItem ->
                LocalDetailCard(sidoItem = sidoItem)
            }
        }
    }
}

@Composable
fun LocalDetailCard(sidoItem: SidoItem) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text(text = sidoItem.stationName,
                    style = TextStyle(fontSize = 18.sp, fontWeight = Bold))
                Spacer(Modifier.padding(4.dp))
                DustGradeSetting(value = sidoItem.pm10Grade)
                Spacer(Modifier.padding(4.dp))
                DustGradeSetting(value = sidoItem.pm25Grade)
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = stringResource(
                    id = R.string.arrow_right))
        }
    }
}

@Composable
fun DustGradeSetting(value: String?) {
    Row {
        value?.let {
            Text(text = "초미세먼지 등급: $it")
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
                fontWeight = Bold)
        } ?: Text(text = "미세먼지 등급: 측정 불가")
    }
}