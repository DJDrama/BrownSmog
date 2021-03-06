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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.dj.brownsmog.ui.dialog.DialogState
import com.dj.brownsmog.ui.dialog.DialogType
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(viewModel: AuthViewModel, onNavigate: (String) -> Unit) {
    val errorMessage = viewModel.errorMessage.collectAsState()
    var dialogState by remember { mutableStateOf(DialogState(false, DialogType.SIMPLE)) }
    if (dialogState.showDialog) {
        ShowDialog(dialogState.dialogType, errorMessage.value ?: "") {
            viewModel.setNoError()
            dialogState = dialogState.copy(showDialog = false)
        }
    }
    errorMessage.value?.let {
        dialogState = DialogState(true, DialogType.SIMPLE)
    }

    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

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
            placeholder = { Text("?????????") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
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
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.login(userId = userId.value.text,
                        password = password.value.text)
                }
            ),
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 24.dp),
            onClick = {
                viewModel.login(userId = userId.value.text,
                    password = password.value.text)
            }
        ) {
            Text(text = "?????????")
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)) {
            Text("?????? ????????? ????????????????")
            TextButton(onClick = {
                val navRoute = AuthScreen.RegisterScreen.route
                onNavigate(navRoute)
            }) {
                Text("????????????", fontWeight = FontWeight.Bold, color = Color.Blue)
            }
        }
    }
}

@Composable
fun ShowDialog(type: DialogType, title: String, onDismiss: () -> Unit) {
    when (type) {
        DialogType.SIMPLE -> {
            AlertDialog(
                text = {
                    Text(title)
                },
                buttons = {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "??????")
                    }
                },
                onDismissRequest = onDismiss
            )
        }
    }
}