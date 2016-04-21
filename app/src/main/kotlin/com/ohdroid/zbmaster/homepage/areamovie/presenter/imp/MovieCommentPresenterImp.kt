package com.ohdroid.zbmaster.homepage.areamovie.presenter.imp

import android.content.Context
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieCommentBusiness
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieDetailView
import com.ohdroid.zbmaster.login.view.LoginActivity
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by ohdroid on 2016/4/12.
 */
class MovieCommentPresenterImp(var context: Context, var dataManager: DataManager, var rxBus: RxBus) : MovieCommentPresenter {


    lateinit var uiView: MovieDetailView
    lateinit var movieInfo: MovieInfo
    var commentList: MutableList<MovieComment> = ArrayList()

    val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun attachView(view: MovieDetailView) {
        uiView = view
        //绑定view的时候绑定rxbus
        subscriptions.add(
                rxBus.toObservable()
                        .subscribe({
                            if (it is MovieCommentBusiness.AddCommentEvent ) {
                                uiView.showAddCommentResult(it.state, it.result)
                            }
                        })
        )
    }

    override fun detachView() {
        //注销rxbus
        subscriptions.clear()
    }

    override fun initMovieInfo(movieInfo: MovieInfo) {
        this.movieInfo = movieInfo
    }


    override fun addComment(commentStr: String, attachMovie: MovieInfo) {

        val userAccount = dataManager.loginManger.getUserAccount()

        if (null == userAccount) {
            //跳转登录界面
            LoginActivity.launch(context)
            return
        }
        val comment: MovieComment = MovieComment(userAccount, commentStr, attachMovie)
        val movieCommentBusiness = MovieCommentBusiness()
        movieCommentBusiness.context = context
        movieCommentBusiness.rxBus = rxBus
        movieCommentBusiness.addComment(comment)
        //        movieCommentBusiness.addComment(object : SaveListener() {
        //            override fun onSuccess() {
        //                rxBus.send(MovieCommentBusiness.AddCommentEvent(context.resources.getString(R.string.hint_add_comment_success), 200))
        //            }
        //
        //            override fun onFailure(p0: Int, p1: String?) {
        //                rxBus.send(MovieCommentBusiness.AddCommentEvent(p1 ?: "failed", p0))
        //            }
        //        }, comment)
    }


    override fun shareMovieInfo() {
        throw UnsupportedOperationException()
    }

    override fun setMovieFavorite() {
        throw UnsupportedOperationException()
    }

    override fun getMovieCommentList() {
        val movieCommentBusiness = MovieCommentBusiness()

        val requestParams = HashMap<String, String>()
        requestParams.put("equalToKey", "movieInfo")
        requestParams.put("equalToValue", movieInfo.objectId)
        requestParams.put("includeInfo", "commentAuthor")
        movieCommentBusiness.requestParams = requestParams
        movieCommentBusiness.context = context
        movieCommentBusiness.getCommentList()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (null == it) {
                        uiView.showEmptyComment()
                        return@filter false
                    }
                    if (it.isEmpty()) {
                        uiView.showEmptyComment()
                        return@filter false
                    }
                    true
                }
                .subscribe(object : Subscriber<MutableList<MovieComment>>() {
                    override fun onNext(t: MutableList<MovieComment>?) {
                        commentList = t!!
                        uiView.showComment(commentList, MovieCommentBusiness.PAGE_LIMIT <= t.size)
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        uiView.showError(-1, e?.message ?: "")
                    }

                })
    }

    override fun getMoreMovieCommentList() {
        val movieCommentBusiness = MovieCommentBusiness()

        val requestParams: MutableMap<String, String> = HashMap()
        requestParams.put("equalToKey", "movieInfo")
        requestParams.put("equalToValue", movieInfo.objectId)
        requestParams.put("includeInfo", "commentAuthor")
        requestParams.put("skip", commentList.size.toString())
        movieCommentBusiness.requestParams = requestParams
        movieCommentBusiness.context = context
        movieCommentBusiness.getCommentList()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (null == it) {
                        uiView.showMoreComment(false)
                        return@filter false
                    }
                    if (it.isEmpty()) {
                        uiView.showMoreComment(false)
                        return@filter false
                    }
                    true
                }
                .subscribe(object : Subscriber<MutableList<MovieComment>>() {
                    override fun onNext(t: MutableList<MovieComment>?) {
                        commentList.addAll(t!!)
                        uiView.showMoreComment(MovieCommentBusiness.PAGE_LIMIT <= t.size)
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        uiView.showError(-1, e?.message ?: "")
                    }

                })
    }


}