package com.ohdroid.zbmaster.homepage.areamovie.data

import android.content.Context
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.BmobDataManager
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import rx.Observable

/**
 * Created by ohdroid on 2016/4/12.
 */
class MovieCommentBusiness : BaseBusiness<MovieComment>() {

    var context: Context? = null

    companion object {
        @JvmField val PAGE_LIMIT = 8
    }

    override fun addItem(comment: MovieComment) {
        BmobDataManager.getInstance().addItem(context!!, comment, object : SaveListener() {
            override fun onSuccess() {
                rxBus?.send(AddCommentEvent(context!!.resources.getString(R.string.hint_add_comment_success), 200))
            }

            override fun onFailure(p0: Int, p1: String?) {
                rxBus?.send(AddCommentEvent(p1 ?: "", p0))
            }
        })
    }

    override fun findList(): Observable<MutableList<MovieComment>> {
        return Observable.create<MutableList<MovieComment>>({
            BmobDataManager.getInstance().findItemList(context!!, requestParams, object : FindListener<MovieComment>() {
                override fun onError(p0: Int, p1: String?) {
                    val e: Throwable = Throwable(p1)
                    it.onError(e)
                }

                override fun onSuccess(p0: MutableList<MovieComment>?) {
                    it.onNext(p0)
                    it.onCompleted()
                }

            })
        })
    }

    class AddCommentEvent(val result: String = "", val state: Int = 0) {
    }

}