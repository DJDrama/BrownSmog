package com.dj.brownsmog.ui.main.local.covid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dj.brownsmog.R
import com.dj.brownsmog.data.model.SidoByulCounter
import com.dj.brownsmog.ui.main.AppBar
import com.dj.brownsmog.ui.main.MainScreen
import com.dj.brownsmog.ui.main.local.brownsmog.SidoItem

@Composable
fun LocalCovidList(viewModel: LocalCovidViewModel, onNavigate: (Any)->Unit) {
    val onLoad = viewModel.onLoad.collectAsState()
    if (!onLoad.value) {
        viewModel.setLoaded()
        viewModel.getLocalCounter()
    }
    val sidoByulList = viewModel.sidoByulItems.collectAsState()
    Column {
        AppBar(
            title = stringResource(R.string.local_covid)
        ){
            onNavigate("Back")
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 0.dp)
        sidoByulList.value?.let{
            LocalList(list = it, onNavigate = onNavigate)
        }
    }
}

@Composable
fun LocalList(list: List<SidoByulCounter>, onNavigate: (String)->Unit){
    LazyColumn {
        items(items = list) { sidoByulCounter ->
            SidoItem(sidoName = sidoByulCounter.countryName, onClick = {
                val navRoute = MainScreen.LocalCovidList.route + "/${sidoByulCounter.countryName}"
                onNavigate(navRoute)
            })
        }
    }
}
