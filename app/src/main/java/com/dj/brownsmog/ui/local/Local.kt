package com.dj.brownsmog.ui.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.dj.brownsmog.data.model.SidoItem

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Local(navController: NavHostController, viewModel: LocalViewModel) {
    //val vm: LocalViewModel = viewModel()
    val sidoItems by viewModel.sidoByulItems.collectAsState()
    sidoItems?.let{
        Text(text = it.joinToString())
    }
}
