package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.BmobDataManager
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment

/**
 * Created by ohdroid on 2016/4/12.
 */
class MovieCommentBusiness : BaseBusiness<MovieComment>() {

    var context: Context? = null

    companion object {
        @JvmField val PAGE_LIMIT = 5
    }

    override fun byGet() {
    }

    override fun byPost() {
    }

    fun addComment(saveListener: SaveListener?, comment: MovieComment) {
        requestParams.put("limit", PAGE_LIMIT.toString())
        BmobDataManager.getInstance().addItem(context!!, comment, saveListener)
    }

    fun getCommentList(findListener: FindListener<MovieComment>) {
        requestParams.put("limit", PAGE_LIMIT.toString())
        BmobDataManager.getInstance().findItemList(context!!, requestParams, findListener)
    }

}