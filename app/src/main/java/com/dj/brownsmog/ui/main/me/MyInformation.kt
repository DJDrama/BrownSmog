package com.dj.brownsmog.ui.main.me

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyInformation(viewModel: UserViewModel) {
    val user = viewModel.user.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp, horizontal = 16.dp)) {
        user.value?.let {
            Text(text = "내 정보",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
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
    }
}

