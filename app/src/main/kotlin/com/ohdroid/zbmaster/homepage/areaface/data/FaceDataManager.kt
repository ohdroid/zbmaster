package com.ohdroid.zbmaster.homepage.areaface.data

import android.content.Context
import android.text.TextUtils
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.utils.UrlUtils
import java.util.*

/**
 * Created by ohdroid on 2016/4/8.
 *
 *presenter层调用，用于向网络请求数据
 */
class FaceDataManager {

    var requestParams: MutableMap<String, String>? = null

    companion object {
        @JvmStatic val faceDataManager: FaceDataManager = FaceDataManager()

        @JvmStatic fun getInstance(): FaceDataManager {
            return faceDataManager
        }
    }

    fun getFaceList(context: Context, params: MutableMap<String, String>?, pageLimit: Int, skip: Int, findListener: FindListener<FaceInfo>) {
        requestParams = params

        val query: BmobQuery<FaceInfo> = BmobQuery();
        query.setLimit(pageLimit)
        query.setSkip(skip)
        query.findObjects(context, object : FindListener<FaceInfo>() {
            //这里包了一下，还要在返回数据中加入七牛的api
            override fun onSuccess(p0: MutableList<FaceInfo>?) {
                addQiniuDomain(p0)
                addQiniuStaticImageApi(p0)
                findListener.onSuccess(p0)
            }

            override fun onError(p0: Int, p1: String?) {
                findListener.onError(p0, p1)
            }

        })
    }

    /**
     * 添加七牛域名
     */
    fun addQiniuDomain(list: MutableList<FaceInfo>?) {
        if (list == null) {
            return
        }

        list.forEach {
            val stringBuffer = StringBuffer()
            stringBuffer.append(QiniuApi.QINIU_URL_DOMAIN)
            stringBuffer.append(it.faceUrl)
            it.faceUrl = stringBuffer.toString()
        }
    }

    /**
     * 添加七牛静态图API
     */
    fun addQiniuStaticImageApi(list: MutableList<FaceInfo>?) {
        if (null == list || requestParams == null) {
            return
        }

        //拼接七牛提供的动图转换静态图API
        //TODO 封装成七牛的API类
        val sb = StringBuilder()
        val method: String? = requestParams!!["method"]

        if (TextUtils.isEmpty(method)) {
            return
        }
        sb.append("$method")
        requestParams!!.remove("method")


        val keys: MutableSet<String> = requestParams!!.keys
        for (key in keys) {
            sb.append("/$key/${requestParams!![key]}")
        }

        println(" 静态图api--->${sb.toString()}")
        list.forEach {
            it.faceUrl = "${it.faceUrl}?${sb.toString()}"
            println("afterchange-->${it.faceUrl}")
        }

    }

    /**
     * 获取图片对应的动图
     */
    fun getDynamicURL(staticUrl: String): String {
        if (staticUrl.indexOf("?") <= 0) {
            //若已经是原始地址那么直接返回
            return staticUrl
        }
        return staticUrl.substring(0, staticUrl.indexOf("?"))
    }
}