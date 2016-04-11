package com.ohdroid.zbmaster.application.data.api

import android.text.TextUtils
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import java.util.*

/**
 * Created by ohdroid on 2016/4/8.
 *
 * 七牛服务器API,方便不熟悉七牛服务器API的童鞋使用
 */
class QiniuApi private constructor() {

    companion object {
        @JvmStatic val QINIU_URL_DOMAIN = "http://7xslkd.com2.z0.glb.clouddn.com"
        @JvmStatic val QINIU_ADVANCE_IAMGE_API = "/imageMogr2"
        @JvmStatic val LOGO_IMAGE_URL = "http://7xslkd.com2.z0.glb.clouddn.com/image/logo/zblogo.png"

        @JvmStatic fun builder(): QiniuApi {
            return QiniuApi()
        }

        @JvmStatic fun getInstace(): QiniuApi {
            return QiniuApi()
        }

    }

    val requestParamters: MutableMap<String, String> = HashMap()

    /**
     * 设置为获取静态图片
     * @param format 设置获取的格式,默认为jpg
     */
    fun setImageStatic(format: String = "jpg"): QiniuApi {
        requestParamters.put("method", "imageMogr2")
        requestParamters.put("format", format)
        return this
    }

    fun setMethod(method: String): QiniuApi {
        requestParamters.put("method", method)
        return this
    }

    /**
     * 设置转换的格式
     */
    fun setFormat(format: String): QiniuApi {
        requestParamters.put("format", format)
        return this
    }


    /**
     *添加其他选项
     */
    fun addOptions(key: String, value: String): QiniuApi {
        requestParamters.put(key, value)
        return this
    }


    /**
     * 构建api parameters
     */
    fun build(): MutableMap<String, String> {
        return requestParamters
    }

    fun getImageUrl(str: String): String {
        return "$QINIU_URL_DOMAIN/$str"
    }


    /**
     * 添加七牛静态图API
     */
    fun buildQiniuStaticImageApi(requestParams: MutableMap<String, String>?): String {
        if ( requestParams == null) {
            return ""
        }

        //拼接七牛提供的动图转换静态图API
        //TODO 封装成七牛的API类
        val sb = StringBuilder()
        val method: String? = requestParams["method"]

        if (TextUtils.isEmpty(method)) {
            return ""
        }
        sb.append("$method")
        requestParams.remove("method")


        val keys: MutableSet<String> = requestParams.keys
        for (key in keys) {
            sb.append("/$key/${requestParams[key]}")
        }

        println(" 静态图api--->${sb.toString()}")
        return sb.toString()

    }

}