package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieGifListBusiness : BaseBusiness<MovieInfo>() {

    lateinit var context: Context


    companion object {
        val PAGE_LIMIT: Int = 6
    }

    override fun byPost() {
        byGet()
    }

    override fun byGet() {
        MovieDataManager.getInstance().getMovieList(context, requestParams, PAGE_LIMIT, startIndex, object : FindListener<MovieInfo>() {
            override fun onError(p0: Int, p1: String?) {
                listener?.onFailed(p0, p1)
            }

            override fun onSuccess(p0: MutableList<MovieInfo>?) {
                listener?.onSuccess(p0)
            }

        })
    }

}
