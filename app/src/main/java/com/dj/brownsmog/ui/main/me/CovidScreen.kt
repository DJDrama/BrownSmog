package com.dj.brownsmog.ui.main.me

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CovidScreen(viewModel: CovidViewModel) {
    val localCounter = viewModel.localCounter.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)) {
        localCounter.value?.let {
            Text(text = "국내현황", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.padding(top = 16.dp))

            Text(text = "국내 확진자수: ${it.totalCase}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "전날대비: ${it.totalCaseBefore}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "국내 완치자수: ${it.totalRecovered}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "국내 사망자수: ${it.totalDeath}명")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "국내 격리자수: ${it.nowCase}명")

            Spacer(modifier = Modifier.padding(top = 16.dp))
            Divider(modifier = Modifier
                .height(1.dp))
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text(text = "국내 검사중: ${it.checkingCounter}명(${it.checkingPercentage}%)")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "국내 검사 결과 양성: ${it.caseCount}명(${it.casePercentage}%)")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "국내 검사 결과 음성: ${it.notCaseCount}명(${it.notCasePercentage}%)")


            Text(text="업데이트 시간: ${it.updateTime}", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

        } ?: run {
            Text(text = "데이터를 불러올 수 없습니다.",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center)
        }
    }
}