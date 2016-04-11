package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieDataManager {
    var requestParams: MutableMap<String, String>? = null


    fun getMovieList(context: Context, params: MutableMap<String, String>?, pageLimit: Int, skip: Int, findListener: FindListener<MovieInfo>) {
        requestParams = params

        val query: BmobQuery<MovieInfo> = BmobQuery();
        query.setLimit(pageLimit)
        query.setSkip(skip)
        query.findObjects(context, object : FindListener<MovieInfo>() {
            //这里包了一下，还要在返回数据中加入七牛的api
            override fun onSuccess(p0: MutableList<MovieInfo>?) {
                addQiniuDomain(p0)
                addQiniuStaticImageApi(p0)
                findListener.onSuccess(p0)
            }

            override fun onError(p0: Int, p1: String?) {
                findListener.onError(p0, p1)
            }

        })
    }

    fun addQiniuStaticImageApi(list: MutableList<MovieInfo>?) {
        if (null == list) {
            return
        }

        QiniuApi.getInstace()


    }

    /**
     * 添加七牛域名
     */
    fun addQiniuDomain(list: MutableList<MovieInfo>?) {
        if (list == null) {
            return
        }

        list.forEach {
            val stringBuffer = StringBuffer()
            stringBuffer.append(QiniuApi.QINIU_URL_DOMAIN)
            stringBuffer.append(it.movieUrl)
            it.movieUrl = stringBuffer.toString()
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