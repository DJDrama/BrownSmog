package com.dj.brownsmog.ui.main.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WhereToVote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dj.brownsmog.R
import com.dj.brownsmog.ui.MapPointerMovingState
import com.dj.brownsmog.util.rememberMapViewWithLifeCycle
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng

@Composable
fun FindLocationScreen(viewModel: HomeViewModel, onUpdateComplete: ()->Unit) {
    val map = rememberMapViewWithLifeCycle()
    val buttonState = remember { mutableStateOf(MapPointerMovingState.DRAGGING) }
    val zoomLevel = remember { 12f }

    val isLocationUpdated = viewModel.locationUpdate.collectAsState()
    if(isLocationUpdated.value){
        viewModel.setUpdateComplete()
        onUpdateComplete()
    }

    val initialLatLng = LatLng(37.55810489467493, 126.99159309267996)
    val initialLocationValue = viewModel.myLocation.collectAsState()
    val initialLocation = initialLocationValue.value?.let {
        LatLng(it.latitude, it.longitude)
    } ?: initialLatLng

    val currentLatLng = remember { mutableStateOf(initialLatLng) }


    Box {
        FindLocationContent(map = map,
            zoomLevel = zoomLevel,
            initialLocation = initialLocation,
            buttonState = buttonState) {
            currentLatLng.value = it
        }
        CurrentPositionIcon(modifier = Modifier.align(Alignment.Center))
        FindLocationFooter(modifier = Modifier.align(Alignment.BottomCenter),
            buttonState = buttonState) {
            viewModel.saveMyLocation(currentLatLng.value)
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun FindLocationContent(
    map: MapView,
    zoomLevel: Float,
    initialLocation: LatLng,
    buttonState: MutableState<MapPointerMovingState>,
    onCameraIdle: (LatLng) -> Unit,
) {
    AndroidView({ map }) { mapView ->
        mapView.getMapAsync { gMap ->
            gMap.isMyLocationEnabled = true
            gMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    initialLocation, zoomLevel
                )
            )
            gMap.setOnCameraMoveStartedListener {
                buttonState.value = MapPointerMovingState.DRAGGING
            }
            gMap.setOnCameraIdleListener {
                buttonState.value = MapPointerMovingState.IDLE
                onCameraIdle(gMap.cameraPosition.target)
            }
        }
    }
}

@Composable
fun CurrentPositionIcon(
    modifier: Modifier,
) {
    Icon(
        modifier = modifier
            .size(48.dp),
        imageVector = Icons.Filled.WhereToVote,
        contentDescription = stringResource(
            id = R.string.where_to_vote))
}

@Composable
fun FindLocationFooter(
    modifier: Modifier = Modifier,
    buttonState: MutableState<MapPointerMovingState>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
    ) {
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (buttonState.value == MapPointerMovingState.DRAGGING) {
                    Color.LightGray
                } else {
                    MaterialTheme.colors.onPrimary
                },
                contentColor = if (buttonState.value == MapPointerMovingState.DRAGGING) {
                    Color.White
                } else {
                    Color.Black
                }),
            onClick = {
                if (buttonState.value == MapPointerMovingState.IDLE) {
                    onClick()
                }
            }
        ) {
            Text(text = "현재 위치로 설정하기")
        }
    }
}