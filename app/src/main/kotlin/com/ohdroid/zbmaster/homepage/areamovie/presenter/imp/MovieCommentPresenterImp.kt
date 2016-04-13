package com.ohdroid.zbmaster.homepage.areamovie.presenter.imp

import android.content.Context
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieCommentBusiness
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieDetailView
import com.ohdroid.zbmaster.login.view.LoginActivity

/**
 * Created by ohdroid on 2016/4/12.
 */
class MovieCommentPresenterImp(var context: Context, var dataManager: DataManager) : MovieCommentPresenter {


    lateinit var uiView: MovieDetailView
    lateinit var movieInfo: MovieInfo


    override fun attachView(view: MovieDetailView) {
        uiView = view
    }

    override fun detachView() {
    }

    override fun initMovieInfo(movieInfo: MovieInfo) {
        this.movieInfo = movieInfo
    }


    override fun addComment(commentStr: String,attachMovie:MovieInfo) {

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
        throw UnsupportedOperationException()
    }

    override fun getMoreMovieCommentList() {
        throw UnsupportedOperationException()
    }
}