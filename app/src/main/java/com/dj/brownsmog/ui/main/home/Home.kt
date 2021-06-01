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
import com.dj.brownsmog.data.model.SidoByulCounter
import com.dj.brownsmog.ui.main.MainScreen
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import java.util.Locale

@Composable
fun Home(viewModel: HomeViewModel, onNavigate: (String) -> Unit) {
    // 위치, 미세먼지, 코로나 정보 데이터를 담는 객체들
    val location = viewModel.myLocation.collectAsState()
    val brownSmogData = viewModel.data.collectAsState()
    val covidInformation = viewModel.covidInformation.collectAsState()

    // Jetpack Compose 코드
    val context = LocalContext.current

    // 위치 권한
    val isPermissionGranted = remember { mutableStateOf(false) }

    // ?.let{} == Java에서는 if(location!=null){} 와 똑같다.
    location.value?.let { // if(location!=null){}
        viewModel.getBrownSmogFromMyLocation(it.latitude, it.longitude)
        viewModel.getCovidCounter(it.latitude, it.longitude)
    } /*?: NoLocationView(text = "결과가 없습니다. 현재 위치를 다시 설정해주세요!", onClick = {
        val navRoute = MainScreen.FindLocation.route
        onNavigate(navRoute)
    })*/

    // 미세먼지 정보와 코로나 정보가 있을 경우에 아래 코드를 실행한다.
    brownSmogData.value?.let {brownSmogData->
        covidInformation.value?.let{sidoByulCounter->
            // 화면에 보여지고 있는 정보들을 나타내는 부분
            BrownSmogContent(brownSmogData, sidoByulCounter) {
                val navRoute = MainScreen.FindLocation.route
                onNavigate(navRoute)
            }
        }
    }


    // 사용자로부터 위도 경도를 구하도록 보여주는 화면
    isPermissionGranted.value = myLocationPermissionGranted(context)
    // 위치 권한이 있는지 물어봅니다.
    if (isPermissionGranted.value) {
        // 허용이 되었다? 사용자의 위도 경도 값이 있는지 다시 한번 확인
        location.value?.let {
            viewModel.getBrownSmogFromMyLocation(it.latitude, it.longitude)
            viewModel.getCovidCounter(it.latitude, it.longitude)
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

// 현재 위치, 미세먼지, 코로나 정보, 현재 날씨 정보를 보여주는 UI
// data는 미세먼지 데이터, sidoByulCounter는 코로나 데이
@Composable
fun BrownSmogContent(data: Data, sidoByulCounter: SidoByulCounter, onLocationReSearch: () -> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 16.dp)) {
        item {

            // Row 가로, Column 세로
            // Bold 굵게
            Row {
                // Padding(end = 16.dp) Padding은 여백 end는 오른쪽
                Text(text = "현재 위치",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 16.dp))
                TextButton(onClick = onLocationReSearch) {
                    Text(text = "내 위치 다시 찾기")
                }
            }
// PAdding의 vertical은 세로 여백 (상, 하 여백)
            Text(text = "${data.city}, ${data.state}", modifier = Modifier.padding(vertical = 8.dp))
            data.location?.let { loc ->
                Text(text = "위도: ${loc.coordinates[0]}")
                Text(text = "경도: ${loc.coordinates[1]}")
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp)) // 여백
            // 한 줄의 구분선 (높이 사이즈는 1)
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
                        // 자바로 따지면 Switch() Case:, Case:, case:
                        // aqicn : 미세먼지
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
                        // aqius : 초미세먼지
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
                // 코로나 정보
                // apply는 '이것을 이용하여'
                sidoByulCounter.apply {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "코로나19 정보",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                    }
                    Text(text = "가장 가까운 위치: $countryName")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "신규 확진자 수: ${newCase}명")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "완치자 수: ${recovered}명")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "발생률: ${percentage}%")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "전일대비증감-지역발생: ${newCcase}명")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "전일대비증감-해외유입: ${newFcase}명")
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

                    // 이미지 나타내기
                    // CoilPainter는 단순히 이미지 로딩 Library 입니다.
                    // http로 시작되는 웹 상에 있는 이미지를 화면에 보여주기 위한 방법
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

// "현재 위치를 지정해주세요"를 보여주는 UI
// () -> Unit : 코틀린 람다 함수 (Java에서도 많이 쓰지만, 코틀린에서 특화되어있다)
@Composable
fun NoLocationView(text: String, onClick: () -> Unit) {
    //  Column 세로로 화면을 보여주는
    // modifier.fillMaxSize()는 화면을 꽉 채워라.
    // verticalArrangement 세로 정렬, horizontal은 가로 정렬
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = text)
        // Spacer는 단순히 여백을 주기 위한 (4.dp) 여백이 들어갔습니다.
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