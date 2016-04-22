package com.ohdroid.zbmaster.application.data.api

import android.text.TextUtils
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.utils.NetUtils
import java.util.*

/**
 * Created by ohdroid on 2016/4/8.
 *
 * 七牛服务器API,方便不熟悉七牛服务器API的童鞋使用
 */
class QiniuApi() {

    companion object {
        @JvmStatic val IMAGE_COMPRESS_LIMIT = 3 * 1024 * 1024//动态图压缩临界值2M


        @JvmStatic val QINIU_URL_DOMAIN = "http://7xslkd.com2.z0.glb.clouddn.com/"
        @JvmStatic val QINIU_ADVANCE_IAMGE_API = "/imageMogr2"
        @JvmStatic val LOGO_IMAGE_URL = "http://7xslkd.com2.z0.glb.clouddn.com/image/logo/zblogo.png"

        @JvmStatic fun builder(): QiniuApi {
            return QiniuApi()
        }

        /**
         * 获取图片对应的动图
         * @param staticUrl:静态网址
         * @param isCompress:是否压缩
         */
        fun getDynamicURL(staticUrl: String, fileSize: Long, isWifi: Boolean): String {
            if (staticUrl.indexOf("?") <= 0) {
                //若已经是原始地址那么直接返回
                return staticUrl
            }
            val original = staticUrl.substring(0, staticUrl.indexOf("?"))

            //由于动图太大，在使用流量的情况下我们压缩36%大小保持80%的分辨率，但是我们标注好对应动图的大小
            //如果是wifi环境我们不进行压缩
            if (!isWifi && fileSize > IMAGE_COMPRESS_LIMIT) {
                val qiniu: QiniuApi = QiniuApi()
                val sb = StringBuilder()
                sb.append(original)
                sb.append("?")
                sb.append(qiniu.getQiniuRequestApiString(qiniu.getReduceApi()))//服务器按百分比压缩
                return sb.toString()
            } else {
                return original
            }
        }
    }

    val requestParamters: MutableMap<String, String> = HashMap()

    /**
     * 获取七牛静态图API
     */
    fun getImageStaticApi(format: String = "jpg"): String {
        requestParamters.clear()
        requestParamters.put("method", "imageMogr2")
        requestParamters.put("format", format)
        return getQiniuRequestApiString(requestParamters)
    }

    fun setMethod(method: String): QiniuApi {
        requestParamters.put("method", method)
        return this
    }

    /**
     *按百分比(1-1000)压缩图片
     */
    fun getReduceApi(percent: Int = 80): MutableMap<String, String> {
        setMethod("imageMogr2")
        addOptions("thumbnail", "!${percent}p")
        return requestParamters
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


    /**
     * 把七牛请求的 map集合转化为str
     */
    fun getQiniuRequestApiString(requestParams: MutableMap<String, String>?): String {
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

        println(" 七牛 api str--->${sb.toString()}")
        return sb.toString()
    }
}