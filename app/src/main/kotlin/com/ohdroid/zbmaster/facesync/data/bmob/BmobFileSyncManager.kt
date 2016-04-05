package com.ohdroid.zbmaster.facesync.data.bmob

import android.content.Context
import cn.bmob.v3.Bmob
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.listener.UploadBatchListener
import cn.bmob.v3.listener.UploadFileListener
import java.io.File

/**
 * Created by ohdroid on 2016/3/22.
 * 基于bmob服务的文件上传工具
 */
class BmobFileSyncManager(ctx: Context) {
    val context = ctx
    lateinit var files: Array<String>

    var startUploadIndex = 0
    var currentUploadIndex = startUploadIndex
    fun uploadFaceFile(fs: Array<String>, uploadFileListener: UploadBatchListener) {
        //由于目前Bmob文件批量上传的有问题
        Bmob.uploadBatch(context, fs, uploadFileListener)
    }

}