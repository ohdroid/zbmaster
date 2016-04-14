package com.ohdroid.zbmaster.homepage.areamovie.presenter.imp

import android.content.Context
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieCommentBusiness
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieDetailView
import com.ohdroid.zbmaster.login.view.LoginActivity
import java.util.*

/**
 * Created by ohdroid on 2016/4/12.
 */
class MovieCommentPresenterImp(var context: Context, var dataManager: DataManager) : MovieCommentPresenter {


    lateinit var uiView: MovieDetailView
    lateinit var movieInfo: MovieInfo
    var commentList: MutableList<MovieComment> = ArrayList()


    override fun attachView(view: MovieDetailView) {
        uiView = view
    }

    override fun detachView() {
    }

    override fun initMovieInfo(movieInfo: MovieInfo) {
        this.movieInfo = movieInfo
    }


    override fun addComment(commentStr: String, attachMovie: MovieInfo) {

        println("==============loginmanager====>${dataManager.loginManger}")
        val userAccount = dataManager.loginManger.getUserAccount()
        println("==============loginmanager====>${dataManager.loginManger}")

        if (null == userAccount) {
            //跳转登录界面
            LoginActivity.launch(context)
            return
        }
        val comment: MovieComment = MovieComment(userAccount, commentStr, attachMovie)
        val movieCommentBusiness = MovieCommentBusiness()
        movieCommentBusiness.context = context
        movieCommentBusiness.addComment(object : SaveListener() {
            override fun onSuccess() {
                println("add comment success")
            }

            override fun onFailure(p0: Int, p1: String?) {
                println("add comment failed:$p0:$p1")
            }
        }, comment)
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

        movieCommentBusiness.getCommentList(object : FindListener<MovieComment>() {
            override fun onSuccess(p0: MutableList<MovieComment>?) {
                if (null == p0) {
                    uiView.showEmptyComment()
                    return
                }
                commentList = p0
                uiView.showComment(commentList, MovieCommentBusiness.PAGE_LIMIT <= p0.size)
            }

            override fun onError(p0: Int, p1: String?) {
                //                uiView.er
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

        movieCommentBusiness.getCommentList(object : FindListener<MovieComment>() {
            override fun onError(p0: Int, p1: String?) {
            }

            override fun onSuccess(p0: MutableList<MovieComment>?) {
                if (null == p0) {
                    uiView.showEmptyComment()
                    return
                }
                commentList.addAll(p0)
                uiView.showMoreComment(MovieCommentBusiness.PAGE_LIMIT <= p0.size)
            }

        })
    }
}