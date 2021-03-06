package com.dj.brownsmog.ui.auth.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dj.brownsmog.ui.auth.AuthScreen
import com.dj.brownsmog.ui.auth.AuthViewModel
import com.dj.brownsmog.ui.dialog.DialogState
import com.dj.brownsmog.ui.dialog.DialogType

@ExperimentalComposeUiApi
@Composable
fun RegisterScreen(viewModel: AuthViewModel, onNavigate: () -> Unit) {
    val errorMessage = viewModel.errorMessage.collectAsState()
    var dialogState by remember { mutableStateOf(DialogState(false, DialogType.SIMPLE)) }
    if (dialogState.showDialog) {
        ShowDialog(dialogState.dialogType, errorMessage.value ?: "") {
            viewModel.setNoError()
            dialogState = dialogState.copy(showDialog = false)
        }
    }
    errorMessage.value?.let{
        dialogState = DialogState(true, DialogType.SIMPLE)
    }
    val (focusRequester1, focusRequester2) = FocusRequester.createRefs()
    val userId = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val nickName = remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = userId.value,
            onValueChange = {
                userId.value = it
            },
            placeholder = { Text("?????????") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester1.requestFocus()
                }
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
            placeholder = { Text("????????????") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester2.requestFocus()
                }
            ),
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester1)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
        )

        OutlinedTextField(
            value = nickName.value,
            onValueChange = {
                nickName.value = it
            },
            placeholder = { Text("?????????") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.register(userId = userId.value.text,
                        password = password.value.text,
                        nickName = nickName.value.text)
                }
            ),
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester2)
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
            Text(text = "????????????")
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)) {
            Text("?????? ????????? ????????????.")
            TextButton(onClick = {
                onNavigate()
            }) {
                Text("?????????", fontWeight = FontWeight.Bold, color = Color.Blue)
            }
        }
    }
}