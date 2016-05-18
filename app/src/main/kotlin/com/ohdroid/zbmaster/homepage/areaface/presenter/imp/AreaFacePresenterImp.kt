package com.ohdroid.zbmaster.homepage.areaface.presenter.imp

import android.app.Activity
import android.content.Context
import android.os.Build
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity
import com.ohdroid.zbmaster.homepage.areaface.data.FaceBusiness
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceView
import com.ohdroid.zbmaster.utils.NetUtils
import com.ohdroid.zbmaster.utils.SystemUtils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/4/6.
 */
class AreaFacePresenterImp(val activity: Activity) : AreaFacePresenter {

    lateinit var uiView: AreaFaceView;
    var mfaceURLList: MutableList<FaceInfo>? = null

    override fun loadFaceList() {
        //检查网络
        if (!NetUtils.isConnected(activity)) {
            println("no net work------------")
            uiView.showErrorView(-1, "no net work")
            //TODO 加载缓存数据
            return
        }


        val faceBusiness: FaceBusiness = FaceBusiness();
        faceBusiness.context = activity//由于是使用bmob请求数据所以这里必须传入context
        faceBusiness.findList()
                .observeOn(AndroidSchedulers.mainThread())
                .filter { //空数据
                    if (it.isEmpty()) {
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
        faceBusiness.context = activity//由于是使用bmob请求数据所以这里必须传入context
        faceBusiness.requestParams.put("skip", mfaceURLList!!.size.toString())
        faceBusiness.findList()
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
                        uiView.showMoreFaceInfo(true)
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
        if (Build.VERSION.SDK_INT >= 23) {
            SystemUtils.checkPermission(activity)
        }
    }

    override fun detachView() {
    }

}