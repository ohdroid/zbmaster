package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
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

    }

}
