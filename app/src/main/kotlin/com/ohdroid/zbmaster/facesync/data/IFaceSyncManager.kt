package com.ohdroid.zbmaster.facesync.data

import cn.bmob.v3.listener.UploadBatchListener
import cn.bmob.v3.listener.UploadFileListener

/**
 * Created by ohdroid on 2016/3/22.
 * 表情同步管理器具有的功能
 */
interface IFaceSyncManager {

    /**
     * 目前是使用的回调的方式来传递上传结果的，后期改成RXBus的形式
     */
    fun uploadFaceFile(uploadFileListener: UploadBatchListener)

}