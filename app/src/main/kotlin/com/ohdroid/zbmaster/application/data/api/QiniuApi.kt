package com.ohdroid.zbmaster.application.data.api

import android.text.TextUtils
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import java.util.*

/**
 * Created by ohdroid on 2016/4/8.
 *
 * 七牛服务器API,方便不熟悉七牛服务器API的童鞋使用
 */
class QiniuApi() {

    companion object {
        @JvmStatic val IMAGE_COMPRESS_LIMIT = 2 * 1024 * 1024//动态图压缩临界值2M


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
        fun getDynamicURL(staticUrl: String, fileSize: Long): String {
            if (staticUrl.indexOf("?") <= 0) {
                //若已经是原始地址那么直接返回
                return staticUrl
            }
            //由于动图太大，我们这里默认让服务器压缩
            val original = staticUrl.substring(0, staticUrl.indexOf("?"))

            if (fileSize > IMAGE_COMPRESS_LIMIT) {
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
     * 设置为获取静态图片
     * @param format 设置获取的格式,默认为jpg
     */
    fun setImageStatic(format: String = "jpg"): QiniuApi {
        requestParamters.put("method", "imageMogr2")
        requestParamters.put("format", format)
        return this
    }

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
     * 设置转换的格式
     */
    fun setFormat(format: String): QiniuApi {
        requestParamters.put("format", format)
        return this
    }

    /**
     *按百分比(1-1000)压缩图片
     */
    fun getReduceApi(percent: Int = 50): MutableMap<String, String> {
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

    /**
     * 添加七牛api
     */
    fun addQiniuApi(list: MutableList<FaceInfo>?, api: String) {
        if (list == null) {
            return
        }

        list.forEach {
            val stringBuffer = StringBuffer()
            stringBuffer.append(QiniuApi.QINIU_URL_DOMAIN)
            stringBuffer.append(it.faceUrl)
            stringBuffer.append("?")
            stringBuffer.append(api)
            it.faceUrl = stringBuffer.toString()
        }
    }

}