package com.dj.brownsmog.data.model



data class Data(
    val city: String,
    val country: String,
    val current: Current?,
    val forecasts: List<Forecast>?,
    val history: History?,
    val location: Location?,
    val state: String,
)


data class Current(
    val pollution: Pollution?,
    val weather: Weather?,
)


data class Forecast(
    val aqicn: Int,
    val aqius: Int,
    val hu: Int,
    val ic: String,
    val pr: Int,
    val tp: Int,
    val tp_min: Int,
    val ts: String,
    val wd: Int,
    val ws: Float,
)

data class History(
    val pollution: List<PollutionX>?,
    val weather: List<WeatherX>?,
)

data class Location(
    val coordinates: List<Double>,
    val type: String,
)

data class N2(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class N2X(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class S2X(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class S2(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class PollutionX(
    val aqicn: Int,
    val aqius: Int,
    val co: CoX?,
    val maincn: String,
    val mainus: String,
    val n2: N2X?,
    val p1: P1X?,
    val p2: P2X?,
    val s2: S2X?,
    val ts: String,
)

data class Pollution(
    val aqicn: Int,
    val aqius: Int,
    val co: Co?,
    val maincn: String,
    val mainus: String,
    val n2: N2?,
    val p1: P1?,
    val p2: P2?,
    val s2: S2?,
    val ts: String,
)


data class Co(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class CoX(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class P2X(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class P2(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class P1X(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class P1(
    val aqicn: Int,
    val aqius: Int,
    val conc: Int,
)

data class WeatherX(
    val hu: Int,
    val ic: String,
    val pr: Int,
    val tp: Int,
    val ts: String,
    val wd: Int,
    val ws: Float,
)

data class Weather(
    val hu: Int,
    val ic: String,
    val pr: Int,
    val tp: Int,
    val ts: String,
    val wd: Int,
    val ws: Float,
)