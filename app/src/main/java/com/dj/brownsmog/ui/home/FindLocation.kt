package com.dj.brownsmog.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.ui.MapPointerMovingState
import com.dj.brownsmog.util.rememberMapViewWithLifeCycle
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng

@Composable
fun FindLocationScreen(viewModel: HomeViewModel) {
    val map = rememberMapViewWithLifeCycle()
    val buttonState = remember { mutableStateOf(MapPointerMovingState.DRAGGING) }
    val zoomLevel = remember { 10f }
    val context = LocalContext.current

    val initialLocationValue = viewModel.myLocation.collectAsState()
    val initialLocation = initialLocationValue.value?.let {
        LatLng(it.latitude, it.longitude)
    } ?: LatLng(37.715133, 126.734086)

    Box {
        FindLocationContent(map = map, zoomLevel = zoomLevel, initialLocation = initialLocation)
    }
}

@SuppressLint("MissingPermission")
@Composable
fun FindLocationContent(map: MapView, zoomLevel: Float, initialLocation: LatLng) {
    AndroidView({ map }) { mapView ->
        mapView.getMapAsync {
            it.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    initialLocation, zoomLevel
                )
            )
            it.isMyLocationEnabled=true
        }
    }
}

@Composable
fun FindLocationFooter() {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
        onClick = {

        }
    ) {
        Text(text = "현재 위치로 설정하기")
    }
}