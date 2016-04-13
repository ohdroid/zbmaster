package com.ohdroid.zbmaster.homepage.areamovie.view

import com.ohdroid.zbmaster.application.BaseView
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieComment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo

/**
 * Created by ohdroid on 2016/4/12.
 */
interface MovieDetailView : BaseView {

    /**
     * 显示评论
     */
    fun showComment(commentList: MutableList<MovieComment>, isHasMore: Boolean)

    /**
     * 显示图片信息
     */
    fun showMovieInfo(movieInfo: MovieInfo)

    /**
     * 显示无评论
     */
    fun showEmptComment()

    /**
     * 显示更多评论
     */
    fun showMoreComment(hasMore: Boolean)

}