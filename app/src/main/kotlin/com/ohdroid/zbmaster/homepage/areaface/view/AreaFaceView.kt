package com.ohdroid.zbmaster.homepage.areaface.view

import com.ohdroid.zbmaster.application.BaseView
import java.util.*

/**
 * Created by ohdroid on 2016/4/5.
 */
interface AreaFaceView : BaseView {

    /**
     * 显示表情列表
     * @param faces 表情url数组
     */
    fun showFaceList(faces: ArrayList<String>);

    /**
     *无数据时，显示空页面
     */
    fun showEmpty()
}