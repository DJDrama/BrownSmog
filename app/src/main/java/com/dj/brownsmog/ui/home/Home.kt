package com.dj.brownsmog.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dj.brownsmog.ui.Screen

@Composable
fun Home(viewModel: HomeViewModel, onNavigate: (String) -> Unit) {

    val location = viewModel.myLocation.collectAsState()
    location.value?.let {

    } ?: NoLocationView(onClick = {
        val navRoute = Screen.FindLocation.route
        onNavigate(navRoute)
    })
}

@Composable
fun NoLocationView(onClick: ()->Unit) {
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
