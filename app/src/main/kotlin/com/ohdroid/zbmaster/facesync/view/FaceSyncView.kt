package com.ohdroid.zbmaster.facesync.view

import com.ohdroid.zbmaster.application.BaseView

/**
 * Created by ohdroid on 2016/3/22.
 */
interface FaceSyncView : BaseView {

    /**
     * 显示同步成功提示
     */
    fun showSyncSuccess()

    /**
     * 显示同步失败提示
     */
    fun showSyncFailed(errorMessage: String)
}
