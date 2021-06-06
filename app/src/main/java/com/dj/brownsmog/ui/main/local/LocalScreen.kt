package com.dj.brownsmog.ui.main.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dj.brownsmog.R
import com.dj.brownsmog.ui.main.MainScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun LocalScreen(onNavigate: (String) -> Unit) {

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Column(modifier = Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .clickable {
                    val navRoute = MainScreen.LocalCovidList.route
                    onNavigate(navRoute)
                }) {
                Icon(imageVector = Icons.Filled.Copyright,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    contentDescription = stringResource(
                        id = R.string.covid))
                Text(text = stringResource(id = R.string.local_covid),
                    modifier = Modifier.padding(top = 4.dp))
            }
            Spacer(modifier = Modifier.padding(32.dp))
            Column(modifier = Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .clickable {
                    val navRoute = MainScreen.LocalBrownSmogList.route
                    onNavigate(navRoute)
                }) {
                Icon(imageVector = Icons.Filled.BlurOn,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    contentDescription = stringResource(
                        id = R.string.covid))
                Text(text = stringResource(id = R.string.local_brown_smog),
                    modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    LocalScreen() {

    }
}
