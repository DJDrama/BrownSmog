package com.dj.brownsmog.ui.main.local.covid

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.R
import com.dj.brownsmog.data.model.SidoByulCounter

@Composable
fun LocalCovidDetailScreen(sidoByulCounter: SidoByulCounter, upPress: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            Box {

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
                    Text(text = sidoByulCounter.countryName,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 0.dp)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(text = "신규 확진자수: ${sidoByulCounter.newCase}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "전체 확진자수: ${sidoByulCounter.totalCase}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "완치자수: ${sidoByulCounter.recovered}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "사망자수: ${sidoByulCounter.death}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "전일대비증감-해외유입: ${sidoByulCounter.newFcase}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "전일대비증감-지역발생: ${sidoByulCounter.newCcase}명")
        }
    }
}
