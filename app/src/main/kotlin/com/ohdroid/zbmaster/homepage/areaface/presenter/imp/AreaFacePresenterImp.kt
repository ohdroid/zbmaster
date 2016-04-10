package com.ohdroid.zbmaster.homepage.areaface.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.application.data.api.QiniuApi
import com.ohdroid.zbmaster.homepage.areaface.data.FaceBusiness
import com.ohdroid.zbmaster.homepage.areaface.data.FaceDataManager
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceView

/**
 * Created by ohdroid on 2016/4/6.
 */
class AreaFacePresenterImp constructor(var context: Context) : AreaFacePresenter {

    lateinit var uiView: AreaFaceView;
    var mfaceURLList: MutableList<FaceInfo>? = null

    override fun loadFaceList() {

        //获取文件列表
        val faceBusiness: FaceBusiness = FaceBusiness();
        faceBusiness.context = context//由于是使用bmob请求数据所以这里必须传入context
        val params = QiniuApi.builder()//使用七牛 API获取静态图片
                .setImageStatic()
                .build()
        faceBusiness.requestParams = params
        faceBusiness.execute(BaseBusiness.METHOD_GET, object : BaseBusiness.BaseResultListener<FaceInfo> {

            override fun onSuccess(faces: MutableList<FaceInfo>?) {
                if (null == faces) {
                    //TODO show empty view
                    println("no face data")
                    return
                }

                println("init data")
                mfaceURLList = faces

                for (index in faces.indices) {
                    println(faces[index].faceUrl)
                }

                uiView.showFaceList(mfaceURLList!!)
                uiView.isHasMoreData(mfaceURLList!!.size >= FaceBusiness.PAGE_LIMIT)
            }

            override fun onFailed(state: Int, errorMessage: String?) {
                println(errorMessage)
            }

        })
    }

    override fun loadMoreFaceInfo() {
        if (mfaceURLList == null) {
            return
        }
        //若无原始数据，或者数量不是页数量的整数
        if (mfaceURLList!!.size == 0 || mfaceURLList!!.size % FaceBusiness.PAGE_LIMIT != 0) {
            uiView.isHasMoreData(false)
            return
        }

        println("presenter load more")

        //获取文件列表
        val faceBusiness: FaceBusiness = FaceBusiness();
        faceBusiness.context = context//由于是使用bmob请求数据所以这里必须传入context
        val params = QiniuApi.builder()//使用七牛 API获取静态图片
                .setImageStatic()
                .build()
        faceBusiness.startIndex = mfaceURLList!!.size
        faceBusiness.requestParams = params
        faceBusiness.execute(BaseBusiness.METHOD_GET, object : BaseBusiness.BaseResultListener<FaceInfo> {

            override fun onSuccess(faces: MutableList<FaceInfo>?) {
                if (null == faces) {
                    uiView.isHasMoreData(false)
                    return
                }

                mfaceURLList!!.addAll(faces)
                uiView.showMoreFaceInfo(faces)

                println("presenter load more success ${faces.size}")
                uiView.isHasMoreData(FaceBusiness.PAGE_LIMIT <= faces.size)

            }

            override fun onFailed(state: Int, errorMessage: String?) {
                println(errorMessage)
            }

        })
    }


    override fun showFaceInfoDetail(position: Int) {
        if (position < 0 || position >= mfaceURLList!!.size) {
            return
        }
        val faceInfo: FaceInfo = mfaceURLList!![position]
        //详情图是动态的所以需要重新获取下URL
        uiView.showFaceInfoDetail(FaceInfo(FaceDataManager.getInstance().getDynamicURL(faceInfo.faceUrl), faceInfo.faceTitle))
    }

    override fun attachView(view: AreaFaceView) {
        this.uiView = view
    }

    override fun detachView() {
    }

}