package com.ohdroid.zbmaster.homepage.areaface.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.homepage.areaface.data.FaceBusiness
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceView
import java.util.*

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
        val params = HashMap<String, String>()//设置为加载静态图
        params.put("method", "imageMogr2")
        params.put("format", "jpg")
        faceBusiness.requestParams = params
        faceBusiness.execute(BaseBusiness.METHOD_GET, object : BaseBusiness.BaseResultListener<FaceInfo> {

            override fun onSuccess(faces: MutableList<FaceInfo>?) {
                if (null == faces) {
                    //TODO show empty view
                    println("no face data")
                    return
                }
                mfaceURLList = faces

                for (index in faces.indices) {
                    println(faces[index].faceUrl)
                }

                if (mfaceURLList!!.size < FaceBusiness.PAGE_LIMIT) {
                    uiView.isHasMoreData(false)
                }
                uiView.showFaceList(mfaceURLList!!)
                uiView.isHasMoreData(true)
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
        val params = HashMap<String, String>()//设置为加载静态图
        params.put("method", "imageMogr2")
        params.put("format", "jpg")
        faceBusiness.startIndex = mfaceURLList!!.size
        faceBusiness.requestParams = params
        faceBusiness.execute(BaseBusiness.METHOD_GET, object : BaseBusiness.BaseResultListener<FaceInfo> {

            override fun onSuccess(faces: MutableList<FaceInfo>?) {
                if (null == faces) {
                    uiView.isHasMoreData(false)
                    return
                }

                println("presenter load more success ${faces.size}")
                if (FaceBusiness.PAGE_LIMIT > faces.size) {
                    uiView.isHasMoreData(false)
                }

                mfaceURLList!!.addAll(faces)
                uiView.showMoreFaceInfo(faces)
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
        //由于采用了七牛的API，这里需要转化成动态的gif图片，要重新构建URL
        val faceInfo: FaceInfo = mfaceURLList!![position]

        println(" dynamic image--->>${getDynamicURL(faceInfo.faceUrl)}")
        uiView.showFaceInfoDetail(FaceInfo(getDynamicURL(faceInfo.faceUrl), faceInfo.faceTitle))
    }

    fun getDynamicURL(staticUrl: String): String {
        if (staticUrl.indexOf("?") <= 0) {
            //若已经是原始地址那么直接返回
            return staticUrl
        }
        return staticUrl.substring(0, staticUrl.indexOf("?"))
    }

    override fun attachView(view: AreaFaceView) {
        this.uiView = view
    }

    override fun detachView() {
    }

}