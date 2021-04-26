package com.dj.brownsmog.ui.main.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dj.brownsmog.ui.main.MainScreen

@Composable
fun Home(viewModel: HomeViewModel, onNavigate: (String) -> Unit) {

    val location = viewModel.myLocation.collectAsState()
    val context = LocalContext.current
    val isPermissionGranted = remember { mutableStateOf(false) }


    if (isPermissionGranted.value) {
        location.value?.let {

        } ?: NoLocationView(onClick = {
            val navRoute = MainScreen.FindLocation.route
            onNavigate(navRoute)
        })
    } else {
        // RequestPermissionHandler(onPermissionResult = { isGranted ->
        //     isPermissionGranted.value = isGranted
        // }, content = { requestPermissionLauncher ->
        //     if (!myLocationPermissionGranted(context)) {
        //         requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        //     }
        // })
    }
}

@Composable
fun NoLocationView(onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "현재 위치를 설정해주세요!")
        Spacer(Modifier.padding(4.dp))
        Button(onClick = onClick) {
            Text(text = "내 위치 찾기")
        }
    }
}

@Composable
fun RequestPermissionHandler(
    onPermissionResult: (Boolean) -> Unit,
    content: @Composable (ActivityResultLauncher<String>) -> Unit,
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted->
        onPermissionResult(isGranted)
    }
    content(requestPermissionLauncher)
}

private fun myLocationPermissionGranted(context: Context): Boolean {
    return arrayOf(Manifest.permission.ACCESS_FINE_LOCATION).all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}