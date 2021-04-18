package com.dj.brownsmog.ui.local

import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.R
import com.dj.brownsmog.data.model.SidoItem

@Composable
fun LocalDetailInfoScreen(
    sidoItem: SidoItem,
    upPress: () -> Unit,
) {
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            IconButton(onClick = upPress,
                modifier = Modifier
                    .padding(16.dp)
                    .size(36.dp)
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center) {
                Text(text = sidoItem.sidoName,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 0.dp)


        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 8.dp)) {
            item {
                DetailBody(sidoItem = sidoItem)
            }
        }
    }
}

@Composable
fun DetailBody(sidoItem: SidoItem) {
    Text(text = sidoItem.stationName,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
    Text(text = "측정일: ${sidoItem.dataTime}",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 13.sp,
        textAlign = TextAlign.End,
        color = Color.LightGray)
    Spacer(Modifier.padding(vertical = 8.dp))
    DustGradeSetting(title = "미세먼지", value = sidoItem.pm10Grade)

    Text(text = "-미세먼지 농도: ${sidoItem.pm10Value}㎍/㎥",
        modifier = Modifier.padding(top = 4.dp),
        color = Color.Gray)
    sidoItem.pm10Value24?.let {
        Text(text = "-24시간 예측 이동 농도: $it㎍/㎥",
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Gray)
    }

    Spacer(Modifier.padding(8.dp))
    DustGradeSetting(title = "초미세먼지", value = sidoItem.pm25Grade)
    Text(text = "-초미세먼지 농도: ${sidoItem.pm25Value}㎍/㎥",
        modifier = Modifier.padding(top = 4.dp),
        color = Color.Gray)
    sidoItem.pm25Value24?.let {
        Text(text = "-24시간 예측 이동 농도: $it㎍/㎥",
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Gray)
    }

    // Others
    Text(text = "기타 정보", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
    Spacer(modifier = Modifier.padding(top = 8.dp))

    Text(text = "-아황산가스 농도: ${sidoItem.so2Value}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-아황산가스 지수: ${sidoItem.so2Grade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-일산화탄소 농도: ${sidoItem.coValue}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-일산화탄소 지수: ${sidoItem.coGrade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-오존 농도: ${sidoItem.o3Value}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-오존 지수: ${sidoItem.o3Grade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-이산화질소 농도: ${sidoItem.no2Value}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-이산화질소 지수: ${sidoItem.no2Grade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-통합대기환경수치: ${sidoItem.khaiValue}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-통합대기환경지수: ${sidoItem.khaiGrade}",
        color = Color.Gray)

    Spacer(modifier = Modifier.padding(top = 16.dp))

}