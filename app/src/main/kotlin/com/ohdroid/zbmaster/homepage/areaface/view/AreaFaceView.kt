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
    fun showFaceList(faces: MutableList<FaceInfo>, hasMore: Boolean);

    /**
     * 显示更多表情
     */
    fun showMoreFaceInfo(hasMore: Boolean)

    /**
     *无数据时，显示空页面
     */
    fun showEmpty()

    /**
     * 显示错误提示页面
     */
    fun showErrorView(errorState: Int, errorMessage: String)

    /**
     * 显示表情详细信息
     */
    fun showFaceInfoDetail(faceInfo: FaceInfo)

    /**
     * 显示简单文字提示
     */
    fun showToastHint(msg: String)
}