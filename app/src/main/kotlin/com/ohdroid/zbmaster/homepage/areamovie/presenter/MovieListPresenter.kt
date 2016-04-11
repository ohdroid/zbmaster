package com.ohdroid.zbmaster.homepage.areamovie.presenter

import com.ohdroid.zbmaster.application.BasePresenter
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieListView

/**
 * Created by ohdroid on 2016/4/11.
 */
interface MovieListPresenter:BasePresenter<MovieListView> {

    /**
     * 显示指定位置的详细信息
     */
    fun showMovieInfoDetail(position: Int)

    /**
     * 显示电影Gif列表
     */
    fun showMovieGifList()

    /**
     * 加载更多movie gif
     */
    fun loadMoreMovieGifList()

}