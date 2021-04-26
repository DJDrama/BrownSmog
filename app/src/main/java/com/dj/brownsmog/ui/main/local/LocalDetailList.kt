package com.dj.brownsmog.ui.main.local

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.R
import com.dj.brownsmog.data.model.SidoItem

@ExperimentalComposeUiApi
@Composable
fun LocalDetailListScreen(
    viewModel: LocalDetailListViewModel,
    sidoName: String,
    onClick: (SidoItem) -> Unit,
) {
    val onLoad = viewModel.onLoad.collectAsState()
    if (!onLoad.value) {
        viewModel.setLoaded()
        viewModel.getSidoByulItems(sidoName = sidoName)
    }
    val sidoItems by viewModel.sidoByulItems.collectAsState()

    val query = remember { mutableStateOf(TextFieldValue("")) }
    val focused = remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        SearchBar(
            modifier = Modifier.focusRequester(focusRequester = focusRequester),
            query = query.value,
            onQueryChange = { query.value = it },
            searchFocused = focused.value,
            onSearchFocusChange = { focused.value = it },
            onClearQuery = {
                query.value = TextFieldValue("")
            },
            keyboardController = keyboardController,
        )
        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 0.dp)
        LocalDetailList(sidoItemList = sidoItems, query = query.value.text, onClick = onClick)
    }
}

@Composable
fun LocalDetailList(sidoItemList: List<SidoItem>?, query: String, onClick: (SidoItem) -> Unit) {
    sidoItemList?.let { list ->
        LazyColumn {
            items(items = list.filter {
                it.stationName.contains(query)
            }) { sidoItem ->
                LocalDetailCard(sidoItem = sidoItem, onClick = onClick)
            }
        }
    }
}

@Composable
fun LocalDetailCard(sidoItem: SidoItem, onClick: (SidoItem) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(sidoItem) })
            .padding(8.dp),
        elevation = 8.dp) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text(text = sidoItem.stationName,
                    style = TextStyle(fontSize = 18.sp, fontWeight = Bold))
                Spacer(Modifier.padding(4.dp))
                DustGradeSetting(title = "미세먼지", value = sidoItem.pm10Grade)
                Spacer(Modifier.padding(4.dp))
                DustGradeSetting(title = "초미세먼지", value = sidoItem.pm25Grade)
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = stringResource(
                    id = R.string.arrow_right))
        }
    }
}

