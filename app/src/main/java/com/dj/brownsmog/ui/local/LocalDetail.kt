package com.dj.brownsmog.ui.local

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.dj.brownsmog.data.model.SidoItem

@Composable
fun LocalDetailScreen(
    navController: NavHostController,
    viewModel: LocalDetailViewModel,
    sidoName: String,
) {
    val onLoad = viewModel.onLoad
    if (!onLoad.value) {
        viewModel.setLoaded()
        viewModel.getSidoByulItems(sidoName = sidoName)
    }
    val sidoItems by viewModel.sidoByulItems.collectAsState()
    LocalDetailCard(sidoItemList = sidoItems)
}

@Composable
fun LocalDetailCard(sidoItemList: List<SidoItem>?) {
    sidoItemList?.let{

    }
}