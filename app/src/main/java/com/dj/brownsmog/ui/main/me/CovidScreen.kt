package com.dj.brownsmog.ui.main.me

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.R

@Composable
fun CovidScreen(viewModel: CovidViewModel, upPress: () -> Unit) {
    val localCounter = viewModel.localCounter.collectAsState()
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
                    Text(text = "????????????",
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
            localCounter.value?.let {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(text = "?????? ????????????: ${it.totalCase}???")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "????????????: ${it.totalCaseBefore}???")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "?????? ????????????: ${it.totalRecovered}???")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "?????? ????????????: ${it.totalDeath}???")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "?????? ????????????: ${it.nowCase}???")

                Spacer(modifier = Modifier.padding(top = 16.dp))
                Divider(modifier = Modifier
                    .height(1.dp))
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(text = "?????? ?????????: ${it.checkingCounter}???(${it.checkingPercentage}%)")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "?????? ?????? ?????? ??????: ${it.caseCount}???(${it.casePercentage}%)")
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "?????? ?????? ?????? ??????: ${it.notCaseCount}???(${it.notCasePercentage}%)")


                Spacer(modifier = Modifier.weight(1f))
                Text(text = it.updateTime,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center)

            } ?: run {
                Text(text = "???????????? ????????? ??? ????????????.",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center)
            }
        }
    }
}