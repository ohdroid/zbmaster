package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.BmobDataManager
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import rx.Observable
import rx.Subscriber

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieGifListBusiness : BaseBusiness<MovieInfo>() {


    lateinit var context: Context


    companion object {
        val PAGE_LIMIT: Int = 8
    }

    override fun findList(): Observable<MutableList<MovieInfo>> {
        return Observable
                .create<MutableList<MovieInfo>> ({ it ->//从bmob服务器请求数据
                    val bmobDataManager = BmobDataManager.getInstance()
                    bmobDataManager.findItemList(context, requestParams, object : FindListener<MovieInfo>() {
                        override fun onError(p0: Int, p1: String?) {
                            val e: Throwable = Throwable(p1)
                            it.onError(e)
                        }

                        override fun onSuccess(p0: MutableList<MovieInfo>?) {
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


    fun addQiniuApi(list: MutableList<MovieInfo>?) {
        if (list == null) {
            return
        }
        val qiniuStaticImageApi = QiniuApi().getImageStaticApi()
        list.forEach {
            it.movieUrl = "${QiniuApi.QINIU_URL_DOMAIN}${it.movieUrl}?$qiniuStaticImageApi"
        }
    }


}
