package com.dj.brownsmog.ui

import android.location.Location
import android.util.Log

val countryMap: Map<String, Pair<Double, Double>> = mapOf(
    "서울" to Pair(126.9780, 37.5665), // 서울
    "부산" to Pair(129.0756, 35.1796), // 부산
    "대구" to Pair(128.6014, 35.8714), // 대구
    "인천" to Pair(126.7052, 37.4563), // 인천
    "광주" to Pair(126.8526, 35.1595), // 광주
    "대전" to Pair(127.3845, 36.3504), // 대전
    "울산" to Pair(129.3114, 35.5384), // 울산
    "세종" to Pair(127.29, 36.48), // 세종
    "경기" to Pair(127.5183, 37.4138), // 경기
    "강원" to Pair(128.1555, 37.8228), // 강원
    "충북" to Pair(127.7000, 36.8000), // 충북
    "충남" to Pair(126.8000, 36.5184), // 충남
    "전북" to Pair(127.1530, 15.7175), // 전북
    "전남" to Pair(126.9910, 34.8679), // 전남
    "경북" to Pair(128.8889, 36.4919), // 경북
    "경남" to Pair(128.2132, 35.4606), // 경남
    "제주" to Pair(126.5312, 33.4996), // 제주
)

fun closestCountryName(lat: Double, lon: Double): String{
    val inputLocation = Location("")
    inputLocation.latitude = lat
    inputLocation.longitude = lon
    var returnValue = "서울"
    var min = Long.MAX_VALUE

    val tempLocation = Location("")
    countryMap.forEach { (k, v) ->
        tempLocation.latitude = v.second
        tempLocation.longitude = v.first

        val distanceDiff = inputLocation.distanceTo(tempLocation) // meter
        if(min>distanceDiff){
            min = distanceDiff.toLong()
            returnValue = k
        }
    }

    return returnValue
}