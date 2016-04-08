package com.ohdroid.zbmaster.application.data.api

import android.text.TextUtils
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

        @JvmStatic fun builder(): QiniuApi {
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

}