package com.dj.brownsmog.ui.main.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dj.brownsmog.data.model.Data
import com.dj.brownsmog.ui.main.MainScreen
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter
import java.util.Locale

@Composable
fun Home(viewModel: HomeViewModel, onNavigate: (String) -> Unit) {

    val location = viewModel.myLocation.collectAsState()
    val brownSmogData = viewModel.data.collectAsState()

    val context = LocalContext.current
    val isPermissionGranted = remember { mutableStateOf(false) }

    location.value?.let {
        viewModel.getBrownSmogFromMyLocation(it.latitude, it.longitude)
    } /*?: NoLocationView(text = "결과가 없습니다. 현재 위치를 다시 설정해주세요!", onClick = {
        val navRoute = MainScreen.FindLocation.route
        onNavigate(navRoute)
    })*/

    brownSmogData.value?.let {
        BrownSmogContent(it) {
            val navRoute = MainScreen.FindLocation.route
            onNavigate(navRoute)
        }
    }

    isPermissionGranted.value = myLocationPermissionGranted(context)
    if (isPermissionGranted.value) {
        location.value?.let {
            viewModel.getBrownSmogFromMyLocation(it.latitude, it.longitude)
        } ?: NoLocationView(text = "현재 위치를 설정해주세요!", onClick = {
            val navRoute = MainScreen.FindLocation.route
            onNavigate(navRoute)
        })
    } else {
        RequestPermissionHandler(
            onPermissionResult = { isGranted ->
                isPermissionGranted.value = isGranted
            }, content = { requestPermissionLauncher ->
                NoLocationView(text = "현재 위치를 설정해주세요!", onClick = {
                    if (!myLocationPermissionGranted(context)) {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        val navRoute = MainScreen.FindLocation.route
                        onNavigate(navRoute)
                    }
                })

            })
    }
}

@Composable
fun BrownSmogContent(data: Data, onLocationReSearch: () -> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 16.dp)) {
        item {
            // Row {
            //     Text(text = "내 위치 다시 찾기")
            // }
            // Spacer(modifier = Modifier.padding(vertical = 8.dp))
            // Divider(modifier = Modifier
            //     .height(1.dp))

            Row {
                Text(text = "현재 위치",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 16.dp))
                TextButton(onClick = onLocationReSearch) {
                    Text(text = "내 위치 다시 찾기")
                }
            }

            Text(text = "${data.city}, ${data.state}", modifier = Modifier.padding(vertical = 8.dp))
            data.location?.let { loc ->
                Text(text = "위도: ${loc.coordinates[0]}")
                Text(text = "경도: ${loc.coordinates[1]}")
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Divider(modifier = Modifier
                .height(1.dp))

            // Current
            data.current?.let {
                // pollution
                it.pollution?.let { pollution ->
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "미세 먼지",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                        Text(text = "측정일: ${pollution.ts.substring(0, 10)}")
                    }

                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(text = "미세먼지 ${pollution.aqicn}",
                            modifier = Modifier.padding(end = 8.dp))
                        Text(text = when (pollution.aqicn) {
                            in 0..50 -> "좋음"
                            in 51..100 -> "보통"
                            in 101..150 -> "민감군에 나쁨"
                            in 151..200 -> "나쁨"
                            in 201..300 -> "매우 나쁨"
                            in 301..500 -> "위험"
                            else -> "위험"
                        }, color = when (pollution.aqicn) {
                            in 0..50 -> Color.Blue
                            in 51..100 -> Color.Green
                            in 101..150 -> Color.Yellow
                            in 151..200 -> Color.Magenta
                            in 201..300 -> Color.Cyan
                            in 301..500 -> Color.Red
                            else -> Color.Red
                        }, fontWeight = FontWeight.Bold)
                    }
                    Row {
                        Text(text = "초미세먼지 ${pollution.aqius}",
                            modifier = Modifier.padding(end = 8.dp))
                        Text(text = when (pollution.aqius) {
                            in 0..50 -> "좋음"
                            in 51..100 -> "보통"
                            in 101..150 -> "민감군에 나쁨"
                            in 151..200 -> "나쁨"
                            in 201..300 -> "매우 나쁨"
                            in 301..500 -> "위험"
                            else -> "위험"
                        }, color = when (pollution.aqius) {
                            in 0..50 -> Color.Blue
                            in 51..100 -> Color.Green
                            in 101..150 -> Color.Yellow
                            in 151..200 -> Color.Magenta
                            in 201..300 -> Color.Cyan
                            in 301..500 -> Color.Red
                            else -> Color.Red
                        }, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Divider(modifier = Modifier.height(1.dp))
                // weather
                it.weather?.let { weather ->
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "현재 날씨",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                        Text(text = "측정일: ${weather.ts.substring(0, 10)}")
                    }
                    Image(
                        painter = rememberCoilPainter("https://www.airvisual.com/images/${weather.ic}.png",
                            fadeIn = true),
                        contentDescription = "Weather Icon",
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "습도: ${weather.hu}%")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "기압: ${weather.pr}hPa")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "온도: ${weather.tp}°C")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "풍향: ${weather.wd}°")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "풍속: ${weather.ws}m/s")
                    Spacer(modifier = Modifier.padding(4.dp))

                }

            }

        }
    }
}

@Composable
fun NoLocationView(text: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = text)
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
    ) { isGranted ->
        onPermissionResult(isGranted)
    }
    content(requestPermissionLauncher)
}

private fun myLocationPermissionGranted(context: Context): Boolean {
    return arrayOf(Manifest.permission.ACCESS_FINE_LOCATION).all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}