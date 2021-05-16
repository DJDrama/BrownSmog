package com.dj.brownsmog.data.response

import com.dj.brownsmog.data.model.Data

data class IqAirResponse(
    val status: String,
    val data: Data,
)