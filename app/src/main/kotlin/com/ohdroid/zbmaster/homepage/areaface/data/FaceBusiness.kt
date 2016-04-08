package com.ohdroid.zbmaster.homepage.areaface.data

import android.content.Context
import cn.bmob.v3.listener.FindListener
import com.ohdroid.zbmaster.application.data.BaseBusiness
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo

/**
 * Created by ohdroid on 2016/4/6.
 *
 * 用于请求表情区数据
 */
class FaceBusiness : BaseBusiness<FaceInfo>() {

    lateinit var context: Context


    /**
     * 每个页面显示数量
     */
    companion object {
        var PAGE_LIMIT = 5
    }

    override fun byPost() {
        byGet()
    }

    override fun byGet() {

        FaceDataManager.getInstance().getFaceList(context, requestParams, PAGE_LIMIT, startIndex, object : FindListener<FaceInfo>() {
            override fun onError(p0: Int, p1: String?) {
                listener?.onFailed(p0, p1)
            }

            override fun onSuccess(p0: MutableList<FaceInfo>?) {
                listener?.onSuccess(p0)
            }

        })

    }
}