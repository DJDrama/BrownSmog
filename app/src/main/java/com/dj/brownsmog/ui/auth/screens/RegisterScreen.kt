package com.dj.brownsmog.ui.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dj.brownsmog.ui.auth.AuthViewModel

@Composable
fun RegisterScreen(viewModel: AuthViewModel) {
    val userId = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val nickName = remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = userId.value,
            onValueChange = {
                userId.value = it
            },
            placeholder = { Text("아이디") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("비밀번호") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
        )

        OutlinedTextField(
            value = nickName.value,
            onValueChange = {
                nickName.value = it
            },
            placeholder = { Text("닉네임") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 24.dp),
            onClick = {
                viewModel.register(userId = userId.value.text,
                    password = password.value.text,
                    nickName = nickName.value.text)
            }
        ) {
            Text(text = "가입하기")
        }
    }
}