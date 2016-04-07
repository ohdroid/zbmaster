package com.ohdroid.zbmaster.homepage.areaface.data

import android.content.Context
import android.text.TextUtils
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.utils.UrlUtils

/**
 * Created by ohdroid on 2016/4/6.
 *
 * 用于请求表情区数据
 */
class FaceBusiness : BaseBusiness<FaceInfo>() {

    lateinit var faceService: FaceService
    lateinit var context: Context


    /**
     * 每个页面显示数量
     */
    companion object {
        var PAGE_LIMIT = 5
    }

    init {
        faceService = retrofit.create(FaceService::class.java)
    }

    override fun byPost() {
        throw UnsupportedOperationException()
    }

    override fun byGet() {

        //通过bmob获取数据
        val query: BmobQuery<FaceInfo> = BmobQuery();
        query.setLimit(PAGE_LIMIT)
        query.setSkip(startIndex)
        query.findObjects(context, object : FindListener<FaceInfo>() {

            override fun onSuccess(p0: MutableList<FaceInfo>?) {
                addQiniuDomain(p0)//由于数据是存在另一个服务器商上面，所以这里需要重新构造下URL
                checkIsLoadStaticImage(p0)//检查是否加载静态图片
                listener?.onSuccess(p0)
            }

            override fun onError(p0: Int, p1: String?) {
                listener?.onFailed(p0, p1)
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

        for (index in list.indices) {
            val stringBuffer = StringBuffer()
            stringBuffer.append(UrlUtils.QINIU_FACE_URL_DOMAIN)
            stringBuffer.append(list[index].faceUrl)
            list[index].faceUrl = stringBuffer.toString()
        }
    }


    fun checkIsLoadStaticImage(list: MutableList<FaceInfo>?) {
        if (null == list) {
            return
        }

        //拼接七牛提供的动图转换静态图API
        //TODO 封装成七牛的API类
        val sb = StringBuilder()
        val method: String? = requestParams["method"]

        if (TextUtils.isEmpty(method)) {
            return
        }

        sb.append("$method")

        val keys: MutableSet<String> = requestParams.keys
        for (key in keys) {
            if (!key.equals("method")) {
                sb.append("/$key/${requestParams[key]}")
            }
        }

        println(" 静态图api--->${sb.toString()}")
        list.forEach {
            it.faceUrl = "${it.faceUrl}?${sb.toString()}"
            println("afterchange-->${it.faceUrl}")
        }

    }


}