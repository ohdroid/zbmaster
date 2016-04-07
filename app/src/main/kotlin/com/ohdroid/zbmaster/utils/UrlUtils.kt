package com.ohdroid.zbmaster.utils

/**
 * Created by ohdroid on 2016/4/6.
 */
class UrlUtils private constructor() {

    val baseUrl: String = "http://113.251.222.9:8080/zbmater/"
    //    val qiniuFaceUrlDomain: String = "httP://http://7xslkd.com2.z0.glb.clouddn.com/"

    /**
     * 表情区地址
     */
    val faceUrl: String = "/gif/face"

    companion object {
        @JvmStatic val QINIU_FACE_URL_DOMAIN = "http://7xslkd.com2.z0.glb.clouddn.com"

        @JvmStatic val utils: UrlUtils = UrlUtils()

        @JvmStatic fun getInstance(): UrlUtils {
            return utils
        }
    }


}