package com.ohdroid.zbmaster.homepage.areaface.presenter

import com.ohdroid.zbmaster.application.BasePresenter
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceView

/**
 * Created by ohdroid on 2016/4/6.
 *
 * 表情模块，主导器
 */
interface AreaFacePresenter : BasePresenter<AreaFaceView> {

    /**
     * 加载表情区数据
     */
    fun loadFaceList()

    /**
     * 显示表情详情
     */
    fun showFaceInfoDetail(position: Int)

    /**
     * 加载更多表情信息
     */
    fun loadMoreFaceInfo()


}