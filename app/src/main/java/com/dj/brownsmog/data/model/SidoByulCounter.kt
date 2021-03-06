package com.dj.brownsmog.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SidoByul(
    val resultCode: String,
    val resultMessage: String,
    val korea: SidoByulCounter,
    val seoul: SidoByulCounter,
    val busan: SidoByulCounter,
    val daegu: SidoByulCounter,
    val incheon: SidoByulCounter,
    val gwangju: SidoByulCounter,
    val daejeon: SidoByulCounter,
    val ulsan: SidoByulCounter,
    val sejong: SidoByulCounter,
    val gyeonggi: SidoByulCounter,
    val gangwon: SidoByulCounter,
    val chungbuk: SidoByulCounter,
    val chungnam: SidoByulCounter,
    val jeonbuk: SidoByulCounter,
    val jeonnam: SidoByulCounter,
    val gyeongbuk: SidoByulCounter,
    val gyeongnam: SidoByulCounter,
    val jeju: SidoByulCounter,
    val quarantine: SidoByulCounter,
) {
    val list: List<SidoByulCounter>
        get() = listOf(korea,
            seoul,
            busan,
            daegu,
            incheon,
            gwangju,
            daejeon,
            daegu,
            incheon,
            gwangju,
            ulsan,
            sejong,
            gyeongbuk,
            gangwon,
            gyeonggi,
            chungbuk,
            chungnam,
            jeonbuk,
            jeonnam,
            gyeongbuk,
            gyeongnam,
            jeju,
            quarantine)
}

@Parcelize
data class SidoByulCounter(
    val countryName: String,
    val newCase: String,
    val totalCase: String,
    val recovered: String,
    val death: String,
    val percentage: String,
    val newFcase: String,
    val newCcase: String,
) : Parcelable