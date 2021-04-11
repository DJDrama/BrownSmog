package com.dj.brownsmog.ui.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.dj.brownsmog.R
import com.dj.brownsmog.data.SidoName
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.ui.Screen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Local(onNavigate: (String) -> Unit) {
    val sidoNames = enumValues<SidoName>().map{
        it.str
    }.toList()
    SidoList(sidoNames, onNavigate=onNavigate)
}

@Composable
fun SidoList(list: List<String>, onNavigate: (String)->Unit) {
    LazyColumn {
        items(items = list) { sidoName ->
            SidoItem(sidoName = sidoName, onClick = {
                val navRoute = Screen.LocalDetail.route + "/${sidoName}"
                onNavigate(navRoute)
            })
        }
    }
}

@Composable
fun SidoItem(sidoName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 8.dp,
        ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Text(text = "지역: $sidoName", modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = stringResource(
                    id = R.string.arrow_right))
        }
    }
}
