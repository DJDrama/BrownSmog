package com.dj.brownsmog.ui.main.me

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.R
import com.dj.brownsmog.ui.main.MainScreen

@Composable
fun MyInformation(viewModel: UserViewModel, onNavigateCovid: (String)->Unit) {
    val user = viewModel.user.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp, horizontal = 16.dp)) {
        user.value?.let {
            Text(text = "내 정보",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = "아이디: ${it.userId}")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "닉네임: ${it.nickname}")
                }
                Column {
                    Button(onClick = {
                        viewModel.logOut()
                    }) {
                        Text(text = "로그아웃")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = {
                        viewModel.exit()
                    }) {
                        Text(text = "탈퇴하기")
                    }
                }
            }

        } ?: Text(text = "알 수 없는 오류 발생!")
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Divider(modifier = Modifier
            .height(1.dp))

        Text(text = "더 보기",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
        Column(modifier = Modifier
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .clickable {
                val navRoute = MainScreen.CovidCounter.route
                onNavigateCovid(navRoute)
            }) {
            Icon(imageVector = Icons.Filled.Copyright,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally),
                contentDescription = stringResource(
                    id = R.string.covid))
            Text(text = stringResource(id = R.string.covid),
                modifier = Modifier.padding(top = 4.dp))
        }

    }
}

