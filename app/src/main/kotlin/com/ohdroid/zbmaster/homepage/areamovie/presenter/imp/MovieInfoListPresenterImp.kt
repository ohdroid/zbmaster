package com.ohdroid.zbmaster.homepage.areamovie.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieDataManager
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieGifListBusiness
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieListView

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieInfoListPresenterImp constructor(var context: Context) : MovieListPresenter {


    lateinit var uiView: MovieListView;
    var mMovieGifList: MutableList<MovieInfo>? = null

    override fun showMovieInfoDetail(position: Int) {
        if (position < 0 || mMovieGifList == null || position >= mMovieGifList!!.size) {
            return
        }
        val movieInfo = mMovieGifList!![position]
        movieInfo.movieUrl = MovieDataManager.getInstance().getDynamicURL(movieInfo.movieUrl)
        uiView.showMovieInfoDetail(movieInfo)
    }

    override fun showMovieGifList() {
        val movieGifListBusiness = MovieGifListBusiness()
        movieGifListBusiness.context = context
        movieGifListBusiness.requestParams = QiniuApi.getInstace().setImageStatic().build()
        movieGifListBusiness.execute(BaseBusiness.METHOD_GET, object : BaseBusiness.BaseResultListener<MovieInfo> {
            override fun onSuccess(results: MutableList<MovieInfo>?) {
                if (null == results) {
                    //TODO show empty view
                    println("no movie data")
                    uiView.showEmpty()
                    return
                }

                println("init movie data")
                mMovieGifList = results
                uiView.showMovieList(mMovieGifList!!, mMovieGifList!!.size >= MovieGifListBusiness.PAGE_LIMIT)
            }

            override fun onFailed(state: Int, errorMessage: String?) {
            }

        })
    }

    override fun loadMoreMovieGifList() {
        val movieGifListBusiness = MovieGifListBusiness()
        movieGifListBusiness.context = context
        movieGifListBusiness.startIndex = mMovieGifList!!.size
        movieGifListBusiness.requestParams = QiniuApi.getInstace().setImageStatic().build()
        movieGifListBusiness.execute(BaseBusiness.METHOD_GET, object : BaseBusiness.BaseResultListener<MovieInfo> {
            override fun onSuccess(results: MutableList<MovieInfo>?) {
                if (null == results) {
                    //TODO show empty view
                    uiView.showEmpty()
                    return
                }

                mMovieGifList!!.addAll(results)
                uiView.showMoreMovieInfo(results.size >= MovieGifListBusiness.PAGE_LIMIT)
            }

            override fun onFailed(state: Int, errorMessage: String?) {
                uiView.showErrorView(state, errorMessage ?: "")
            }

        })
    }

    override fun attachView(view: MovieListView) {
        this.uiView = view
    }

    override fun detachView() {
    }

}