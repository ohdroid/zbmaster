package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.data.api.BmobDataManager
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieDataManager {
    var requestParams: MutableMap<String, String>? = null


    companion object {
        private val manager: MovieDataManager = MovieDataManager()
        @JvmStatic fun getInstance(): MovieDataManager {
            return manager
        }
    }

    /**
     * 获取movie动图列表
     */
    fun getMovieList(context: Context, params: MutableMap<String, String>?, pageLimit: Int, skip: Int, findListener: FindListener<MovieInfo>) {
        requestParams = params

        //        val query: BmobQuery<MovieInfo> = BmobQuery()
        //        query.setLimit(pageLimit)
        //        query.setSkip(skip)
        //        query.findObjects(context, object : FindListener<MovieInfo>() {
        //            //这里包了一下，还要在返回数据中加入七牛的api
        //            override fun onSuccess(p0: MutableList<MovieInfo>?) {
        //                addQiniuDomain(p0)
        //                addQiniuStaticImageApi(p0)
        //                findListener.onSuccess(p0)
        //            }
        //
        //            override fun onError(p0: Int, p1: String?) {
        //                findListener.onError(p0, p1)
        //            }
        //
        //        })

        val bmobDataManager = BmobDataManager.getInstance()
        bmobDataManager.findItemList(context, params, object : FindListener<MovieInfo>() {
            override fun onError(p0: Int, p1: String?) {
                findListener.onError(p0, p1)
            }

            override fun onSuccess(p0: MutableList<MovieInfo>?) {
                addQiniuDomain(p0)
                addQiniuStaticImageApi(p0)
                findListener.onSuccess(p0)
            }

        })
    }


    fun <T : BmobObject> addItem(context: Context, saveListener: SaveListener, t: T) {
        t.save(context, saveListener)
    }

    /**
     * 为movie动图添加评论
     */
    fun addMovieComment(context: Context, requestParams: MutableMap<String, String>, comment: MovieComment, movieInfo: MovieInfo, author: AccountInfo) {
        //        comment.commentAuthor =
    }

    /**
     * 获取电影区对应的评论
     */
    fun getMovieCommentList(context: Context, pageLimit: Int, skip: Int, findListener: FindListener<MovieComment>) {
        val query: BmobQuery<MovieComment> = BmobQuery()
        query.setLimit(pageLimit)
        query.setSkip(skip)
        query.findObjects(context, findListener)
    }

    fun addQiniuStaticImageApi(list: MutableList<MovieInfo>?) {
        if (null == list) {
            return
        }

        val qiniuApi = QiniuApi().buildQiniuStaticImageApi(requestParams)
        list.forEach {
            it.movieUrl = "${it.movieUrl}?$qiniuApi"
            println("afterchange-->${it.movieUrl}")
        }

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