package com.dj.brownsmog.network

import com.dj.brownsmog.data.model.BrownSmogItem
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.data.response.AirKoreaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApiRetrofitService {

    /**
     * http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty
     * ?serviceKey=DMA%2FsZpigIsTg7562fuFFcG7Sjt5biOTgJQdQsgJMVKmgKMVv4TP0Ldn8x3IcZhUGoskLDrBw4Vb4dPVcaVmkA%3D%3D
     * &returnType=json
     * &numOfRows=1000
     * &pageNo=1
     * &sidoName=%EC%A0%84%EA%B5%AD
     * &ver=1.0
     */
    @GET("B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
    suspend fun getSidobyulBrownSmog(
        //시도별 미세먼지 현황
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 1000,
        @Query("sidoName") sidoName: String,
        @Query("returnType") returnType: String = JSON,
        @Query("serviceKey") serviceKey: String,
        @Query("ver") version: String = VERSION,
    ): Response<AirKoreaResponse<SidoItem>>

    //@GET("B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
    //@GET("B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
    @GET("B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty")
    suspend fun getBrownSmogFromMyLocation(
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String = JSON,
        @Query("sidoName") sidoName: String,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 1,
        @Query("dataTerm") dataTerm: String = "DAILY",
        @Query("ver") version: String = VERSION,
    ): Response<AirKoreaResponse<BrownSmogItem>>
}