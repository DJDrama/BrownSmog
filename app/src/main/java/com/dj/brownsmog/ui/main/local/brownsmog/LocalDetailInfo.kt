package com.dj.brownsmog.ui.main.local.brownsmog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.dj.brownsmog.ui.main.local.DustGradeSetting

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
    Text(text = "?????????: ${sidoItem.dataTime}",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 13.sp,
        textAlign = TextAlign.End,
        color = Color.LightGray)
    Spacer(Modifier.padding(vertical = 8.dp))
    DustGradeSetting(title = "????????????", value = sidoItem.pm10Grade)

    Text(text = "-???????????? ??????: ${sidoItem.pm10Value}???/???",
        modifier = Modifier.padding(top = 4.dp),
        color = Color.Gray)
    sidoItem.pm10Value24?.let {
        Text(text = "-24?????? ?????? ?????? ??????: $it???/???",
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Gray)
    }

    Spacer(Modifier.padding(8.dp))
    DustGradeSetting(title = "???????????????", value = sidoItem.pm25Grade)
    Text(text = "-??????????????? ??????: ${sidoItem.pm25Value}???/???",
        modifier = Modifier.padding(top = 4.dp),
        color = Color.Gray)
    sidoItem.pm25Value24?.let {
        Text(text = "-24?????? ?????? ?????? ??????: $it???/???",
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Gray)
    }

    // Others
    Text(text = "?????? ??????", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
    Spacer(modifier = Modifier.padding(top = 8.dp))

    Text(text = "-??????????????? ??????: ${sidoItem.so2Value}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-??????????????? ??????: ${sidoItem.so2Grade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-??????????????? ??????: ${sidoItem.coValue}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-??????????????? ??????: ${sidoItem.coGrade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-?????? ??????: ${sidoItem.o3Value}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-?????? ??????: ${sidoItem.o3Grade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-??????????????? ??????: ${sidoItem.no2Value}ppm",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-??????????????? ??????: ${sidoItem.no2Grade}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))

    Text(text = "-????????????????????????: ${sidoItem.khaiValue}",
        color = Color.Gray)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Text(text = "-????????????????????????: ${sidoItem.khaiGrade}",
        color = Color.Gray)

    Spacer(modifier = Modifier.padding(top = 16.dp))

}