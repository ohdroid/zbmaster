package com.ohdroid.zbmaster.homepage.areaface.data

import android.content.Context
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.api.BmobDataManager
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import rx.Observable

/**
 * Created by ohdroid on 2016/4/8.
 *
 *presenter层调用，用于向网络请求数据，由于同时使用了bmob服务器和七牛服务器,所以这里单独写成类，若是同一API类型可以使用retrofit
 */
class FaceDataManager {

    var requestParams: MutableMap<String, String>? = null

    companion object {
        @JvmStatic protected val faceDataManager: FaceDataManager = FaceDataManager()

        @JvmStatic fun getInstance(): FaceDataManager {
            return faceDataManager
        }
    }

    fun getFaceList(context: Context, params: MutableMap<String, String>?, pageLimit: Int, skip: Int, findListener: FindListener<FaceInfo>) {
        requestParams = params
        requestParams!!.put("limit", pageLimit.toString())
        val bmobDataManger = BmobDataManager.getInstance()
        bmobDataManger.findItemList(context, params, object : FindListener<FaceInfo>() {
            override fun onError(p0: Int, p1: String?) {
                findListener.onError(p0, p1)
            }

            override fun onSuccess(p0: MutableList<FaceInfo>?) {
                addQiniuApi(p0)
                //                addQiniuStaticImageApi(p0)
                findListener.onSuccess(p0)
            }

        })
    }

    /**
     * rx方式获取数据
     */
    fun getFaceList(context: Context, params: MutableMap<String, String>?): Observable<MutableList<FaceInfo>> {
        requestParams = params
        return Observable
                .create<MutableList<FaceInfo>>({
                    val bmobDataManager = BmobDataManager.getInstance()
                    bmobDataManager.findItemList(context, params, object : FindListener<FaceInfo>() {
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

    /**
     * 获取图片对应的动图
     * @param staticUrl:静态网址
     * @param isCompress:是否压缩
     */
    fun getDynamicURL(staticUrl: String, isCompress: Boolean): String {
        if (staticUrl.indexOf("?") <= 0) {
            //若已经是原始地址那么直接返回
            return staticUrl
        }
        //由于动图太大，我们这里默认让服务器压缩
        val original = staticUrl.substring(0, staticUrl.indexOf("?"))

        if (isCompress) {
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