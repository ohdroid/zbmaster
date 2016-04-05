package com.ohdroid.zbmaster.facesync.presenter

import com.ohdroid.zbmaster.application.BasePresenter
import com.ohdroid.zbmaster.facesync.view.FaceSyncView

/**
 * Created by ohdroid on 2016/3/22.
 */
interface FaceSyncPresenter : BasePresenter<FaceSyncView> {

    /**
     * 同步表情
     */
    fun syncFace()

}