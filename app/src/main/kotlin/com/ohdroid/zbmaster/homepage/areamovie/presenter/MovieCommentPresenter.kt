package com.ohdroid.zbmaster.homepage.areamovie.presenter

import com.ohdroid.zbmaster.application.BasePresenter
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieDetailView

/**
 * Created by ohdroid on 2016/4/12.
 */
interface MovieCommentPresenter : BasePresenter<MovieDetailView> {

    /**
     * 初始化评论关联的movie
     */
    fun initMovieInfo(movieInfo: MovieInfo)

    /**
     *显示评论
     */
    fun getMovieCommentList()

    /**
     * 显示更多的评论信息
     */
    fun getMoreMovieCommentList()

    /**
     * 添加电影评论
     */
    fun addComment(commentStr: String, attachMovie: MovieInfo)

    /**
     * 分享电影gif
     */
    fun shareMovieInfo()

    /**
     * 收藏电影gif
     */
    fun setMovieFavorite()
}