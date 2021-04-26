package com.dj.brownsmog.ui.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dj.brownsmog.R
import com.dj.brownsmog.ui.auth.AuthScreen
import com.dj.brownsmog.ui.auth.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel, onNavigate: (String) -> Unit) {
    val userId = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.brownsmog),
            modifier = Modifier
                .padding(top = 24.dp)
                .width(150.dp)
                .height(150.dp),
            contentDescription = stringResource(id = R.string.brown_smog)
        )

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
                viewModel.login(userId = userId.value.toString(),
                    password = password.value.toString())
            }
        ) {
            Text(text = "로그인")
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)) {
            Text("아직 계정이 없으신가요?")
            TextButton(onClick = {
                val navRoute = AuthScreen.RegisterScreen.route
                onNavigate(navRoute)
            }) {
                Text("가입하기", color = Color.Blue)
            }
        }
    }
}
