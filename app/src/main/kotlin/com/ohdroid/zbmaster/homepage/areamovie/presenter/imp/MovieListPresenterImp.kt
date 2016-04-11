package com.ohdroid.zbmaster.homepage.areamovie.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieListView

/**
 * Created by ohdroid on 2016/4/11.
 */
class MovieListPresenterImp constructor(var context: Context) : MovieListPresenter {


    lateinit var uiView: MovieListView;
    var mfaceURLList: MutableList<FaceInfo>? = null

    override fun showMovieInfoDetail(position: Int) {
        val movieInfo: MovieInfo = MovieInfo("", "title")
        uiView.showMovieInfoDetail(movieInfo)
    }

    override fun showMovieGifList() {
    }

    override fun loadMoreMovieGifList() {
    }

    override fun attachView(view: MovieListView) {
        this.uiView = view
    }

    override fun detachView() {
    }

}