package com.dj.brownsmog.ui.main.me

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun MyInformation(viewModel: UserViewModel) {
    val user = viewModel.user.collectAsState()
    Column() {
        user.value?.let {
            Text(text = "아이디: ${it.userId}")
            Text(text = "닉네임: ${it.nickname}")
            Button(onClick = {
                viewModel.logOut()
            }){
                Text(text = "로그아웃")
            }
        } ?: Text(text="알 수 없는 오류 발생!")
    }
}