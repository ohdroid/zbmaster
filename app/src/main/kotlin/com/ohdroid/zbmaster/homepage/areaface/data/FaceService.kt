package com.ohdroid.zbmaster.homepage.areaface.data

import com.ohdroid.zbmaster.homepage.areaface.data.model.FaceInfoWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by ohdroid on 2016/4/6.
 *
 * retorfit 请求表情接口
 */
interface FaceService {
    /**
     * 请求表情区域的静态图片
     */
    @GET("gif/face")
    fun byGetStaticFaceList(@QueryMap options: Map<String, String>): Call<FaceInfoWrapper>

    /**
     * 请求表情区域的静态图片不带参数
     */
    @GET("gif/face")
    fun byGetStaticFaceListWithoutOptions(): Call<FaceInfoWrapper>

}