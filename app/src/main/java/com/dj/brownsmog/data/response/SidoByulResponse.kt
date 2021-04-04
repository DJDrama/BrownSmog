package com.dj.brownsmog.data.response

import com.dj.brownsmog.data.model.SidoItem

data class SidoByulResponse(
    val response: BodyHeader
)

data class BodyHeader(
    val body: Body,
    val header: Header,
)

data class Body(
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int,
    val items: List<SidoItem>,
)

data class Header(
    val resultMsg: String,
    val resultCode: String,
)