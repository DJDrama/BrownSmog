package com.dj.brownsmog.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController

@Composable
fun Home(viewModel: HomeViewModel) {
    val location = viewModel.myLocation.collectAsState()
    location.value?.let{

    } ?: NoLocationView()
}

@Composable
fun NoLocationView(){

}
