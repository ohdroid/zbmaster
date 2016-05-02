package com.ohdroid.zbmaster.homepage.areaface.data

import android.content.Context
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.BmobDataManager
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import rx.Observable

/**
 * Created by ohdroid on 2016/4/6.
 *
 * 用于请求表情区数据
 */
class FaceBusiness : BaseBusiness<FaceInfo>() {


    lateinit var context: Context


    /**
     * 每个页面显示数量
     */
    companion object {
        val PAGE_LIMIT = 5
    }

    /**
     * 查询搞笑表情gif图数据
     */
    override fun findList(): Observable<MutableList<FaceInfo>> {
        requestParams["limit"] = PAGE_LIMIT.toString()
        return Observable
                .create<MutableList<FaceInfo>>({
                    val bmobDataManager = BmobDataManager.getInstance()
                    bmobDataManager.findItemList(context, requestParams, object : FindListener<FaceInfo>() {
                        override fun onError(p0: Int, p1: String?) {
                            val e: Throwable = Throwable(p1)
                            it.onError(e)
                        }

                        override fun onSuccess(p0: MutableList<FaceInfo>?) {
                            it.onNext(p0)
                            it.onCompleted()
                        }

                    })
                })
                .map({ //对数据二次编辑，融合七牛服务器api
                    addQiniuApi(it)
                    it
                })
    }

    /**
     * 添加七牛api
     */
    fun addQiniuApi(list: MutableList<FaceInfo>?) {
        if (list == null) {
            return
        }
        val qiniuStaticImageApi = QiniuApi().getImageStaticApi()
        list.forEach {
            it.faceUrl = "${QiniuApi.QINIU_URL_DOMAIN}${it.faceUrl}?$qiniuStaticImageApi"
        }
    }
}