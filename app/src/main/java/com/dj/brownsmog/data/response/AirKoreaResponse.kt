package com.dj.brownsmog.data.response

import com.dj.brownsmog.data.model.SidoItem

data class AirKoreaResponse<T>(
    val response: BodyHeader<T>
)

data class BodyHeader<T>(
    val body: Body<T>,
    val header: Header,
)

data class Body<T>(
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int,
    val items: List<T>,
)

data class Header(
    val resultMsg: String,
    val resultCode: String,
)