package com.ohdroid.zbmaster.homepage.areamovie.presenter.imp

import android.content.Context
import cn.bmob.v3.BmobObject
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieDataManager
import com.ohdroid.zbmaster.homepage.areamovie.data.MovieGifListBusiness
import com.ohdroid.zbmaster.homepage.areamovie.model.MovieInfo
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter
import com.ohdroid.zbmaster.homepage.areamovie.view.MovieListView
import com.ohdroid.zbmaster.utils.NetUtils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.internal.util.SubscriptionList
import rx.schedulers.Schedulers

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
        val movieInfo = mMovieGifList!![position]//由于序列化过,所以这里直接传过去
        uiView.showMovieInfoDetail(movieInfo)
    }

    override fun showMovieGifList() {
        //网络检查
        if (!NetUtils.isConnected(context)) {
            uiView.showErrorView(-1, context.resources.getString(R.string.hint_no_net_work))
            return
        }

        val movieGifListBusiness = MovieGifListBusiness()
        movieGifListBusiness.context = context
        //默认使用GET方式
        movieGifListBusiness.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (null == it) {
                        return@filter false
                    }
                    if (it.isEmpty()) {
                        uiView.showEmpty()
                    }

                    !it.isEmpty()
                }
                .subscribe(object : Subscriber<MutableList<MovieInfo>>() {
                    override fun onNext(t: MutableList<MovieInfo>?) {
                        mMovieGifList = t
                        uiView.showMovieList(mMovieGifList!!, mMovieGifList!!.size >= MovieGifListBusiness.PAGE_LIMIT)
                    }

                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable?) {
                        uiView.showErrorView(0, e?.message ?: "")
                    }

                })
    }

    override fun loadMoreMovieGifList() {
        if (null == mMovieGifList || mMovieGifList!!.size == 0) {
            uiView.showMoreMovieInfo(false)
            return
        }

        val movieGifListBusiness = MovieGifListBusiness()
        movieGifListBusiness.context = context
        movieGifListBusiness.requestParams.put("skip", mMovieGifList?.size.toString())
        movieGifListBusiness.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<MutableList<MovieInfo>>() {
                    override fun onError(e: Throwable?) {
                        uiView.showMoreMovieInfo(true)
                        uiView.showErrorView(-1, e?.message ?: "")
                    }

                    override fun onNext(results: MutableList<MovieInfo>?) {
                        mMovieGifList!!.addAll(results!!)
                        uiView.showMoreMovieInfo(results.size >= MovieGifListBusiness.PAGE_LIMIT)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    override fun attachView(view: MovieListView) {
        this.uiView = view
    }

    override fun detachView() {
    }

}