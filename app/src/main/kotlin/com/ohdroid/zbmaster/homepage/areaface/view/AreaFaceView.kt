package com.ohdroid.zbmaster.homepage.areaface.view

import com.ohdroid.zbmaster.application.BaseView
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo

/**
 * Created by ohdroid on 2016/4/5.
 */
interface AreaFaceView : BaseView {

    /**
     * 显示表情列表
     * @param faces 表情url数组
     */
    fun showFaceList(faces: MutableList<FaceInfo>);

    /**
     * 显示更多表情
     */
    fun showMoreFaceInfo(faces: MutableList<FaceInfo>)

    /**
     * 设置是否有更多数据
     */
    fun isHasMoreData(hasMore: Boolean)

    /**
     *无数据时，显示空页面
     */
    fun showEmpty()


    /**
     * 显示表情详细信息
     */
    fun showFaceInfoDetail(faceInfo: FaceInfo)
}