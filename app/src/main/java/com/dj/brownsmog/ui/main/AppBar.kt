package com.dj.brownsmog.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dj.brownsmog.R

@Composable
fun AppBar(title: String, onBackPressed: ()->Unit){
    Row( modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 12.dp, vertical = 8.dp)){

        IconButton(onClick = {
           onBackPressed()
        }) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(R.string.label_back)
            )
        }

        Text(text = title, fontSize=20.sp, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Preview
@Composable
fun AppBarPreview(){
    AppBar(title = "Title"){

    }
}