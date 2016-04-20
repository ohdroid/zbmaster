package com.ohdroid.zbmaster.homepage.areaface.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.data.FaceBusiness
import com.ohdroid.zbmaster.homepage.areaface.data.FaceDataManager
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ohdroid on 2016/4/6.
 */
class AreaFacePresenterImp constructor(var context: Context) : AreaFacePresenter {

    lateinit var uiView: AreaFaceView;
    var mfaceURLList: MutableList<FaceInfo>? = null
    /**
     * 压缩零界值
     */
    val COMPRESS_SIZE = 2 * 1024 * 1024

    override fun loadFaceList() {

        //获取文件列表
        val faceBusiness: FaceBusiness = FaceBusiness();
        faceBusiness.context = context//由于是使用bmob请求数据所以这里必须传入context
        faceBusiness.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .filter { //空数据
                    if ( it.isEmpty()) {
                        uiView.showEmpty()
                    }
                    !it.isEmpty()
                }
                .subscribe(object : Subscriber<MutableList<FaceInfo>>() {
                    override fun onNext(faces: MutableList<FaceInfo>?) {
                        mfaceURLList = faces

                        for (index in faces!!.indices) {
                            println(faces[index].faceUrl)
                        }

                        uiView.showFaceList(mfaceURLList!!, mfaceURLList!!.size >= FaceBusiness.PAGE_LIMIT)
                    }

                    override fun onError(e: Throwable?) {
                        uiView.showErrorView(-1, e.toString())
                    }

                    override fun onCompleted() {
                    }
                })

    }

    override fun loadMoreFaceInfo() {
        if (mfaceURLList == null) {
            return
        }
        //若无原始数据，或者数量不是页数量的整数
        if (mfaceURLList!!.size == 0 || mfaceURLList!!.size % FaceBusiness.PAGE_LIMIT != 0) {
            return
        }

        val faceBusiness: FaceBusiness = FaceBusiness();
        faceBusiness.context = context//由于是使用bmob请求数据所以这里必须传入context
        faceBusiness.requestParams.put("skip", mfaceURLList!!.size.toString())
        faceBusiness.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (null == it) {
                        uiView.showMoreFaceInfo(false)
                        return@filter false
                    }
                    if (it.isEmpty()) {
                        uiView.showMoreFaceInfo(false)
                    }
                    !it.isEmpty()
                }
                .subscribe(object : Subscriber<MutableList<FaceInfo>>() {
                    override fun onError(e: Throwable?) {
                        uiView.showErrorView(-1, e?.message ?: "")
                    }

                    override fun onNext(faces: MutableList<FaceInfo>?) {
                        mfaceURLList!!.addAll(faces!!)//添加数据到内存
                        if (faces.size < FaceBusiness.PAGE_LIMIT) {
                            //更新UI显示
                            uiView.showMoreFaceInfo(false)
                        } else {
                            uiView.showMoreFaceInfo(true)
                        }
                    }

                    override fun onCompleted() {
                    }

                })
    }


    override fun showFaceInfoDetail(position: Int) {
        if (position < 0 || position >= mfaceURLList!!.size) {
            return
        }

        uiView.showFaceInfoDetail(mfaceURLList!![position])
    }

    override fun attachView(view: AreaFaceView) {
        this.uiView = view
    }

    override fun detachView() {
    }

}